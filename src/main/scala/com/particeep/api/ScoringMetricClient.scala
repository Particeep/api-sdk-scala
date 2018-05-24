package com.particeep.api

import com.particeep.api.core._
import com.particeep.api.models.{ ErrorResult, PaginatedSequence, TableSearch }
import com.particeep.api.models.scoring_metrics._
import com.particeep.api.utils.LangUtils
import play.api.libs.json.Json

import scala.concurrent.{ ExecutionContext, Future }

trait ScoringMetricsCapability {
  self: WSClient =>

  val scoring_metric: ScoringMetricClient = new ScoringMetricClient(this)

  def scoring_metric(credentials: ApiCredential): ScoringMetricClient = new ScoringMetricClient(this, Some(credentials))
}

object ScoringMetricClient {
  private val endPoint: String = "/scoring-metrics"
  private implicit val scoring_evaluation_format = ScoringEvaluation.format
  private implicit val scoring_evaluation_creation_format = ScoringEvaluationCreation.format
  private implicit val scoring_metric_format = ScoringMetric.format
}

class ScoringMetricClient(val ws: WSClient, val credentials: Option[ApiCredential] = None) extends WithWS with WithCredentials with EntityClient {

  import ScoringMetricClient._

  def runEvaluation(
    metric_id:   String,
    se_creation: ScoringEvaluationCreation,
    timeout:     Long                      = defaultTimeOut
  )(implicit exec: ExecutionContext): Future[Either[ErrorResult, ScoringEvaluation]] = {
    ws.post[ScoringEvaluation](s"$endPoint/$metric_id/evals", timeout, Json.toJson(se_creation))
  }

  def searchMetrics(timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Seq[ScoringMetric]]] = {
    ws.get[Seq[ScoringMetric]](s"$endPoint/search/", timeout)
  }

  def metricsById(metric_id: String, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, ScoringMetric]] = {
    ws.get[ScoringMetric](s"$endPoint/$metric_id", timeout)
  }

  def searchEvaluations(
    criteria:       ScoringEvaluationSearch,
    table_criteria: TableSearch,
    timeout:        Long                    = defaultTimeOut
  )(implicit exec: ExecutionContext): Future[Either[ErrorResult, PaginatedSequence[ScoringEvaluation]]] = {
    ws.get[PaginatedSequence[ScoringEvaluation]](
      s"$endPoint/evals/search",
      timeout,
      LangUtils.productToQueryString(criteria) ++ LangUtils.productToQueryString(table_criteria)
    )
  }

  def evaluationsById(eval_id: String, metric_id: String, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, ScoringEvaluation]] = {
    ws.get[ScoringEvaluation](s"$endPoint/$metric_id/evals/$eval_id", timeout)
  }
}
