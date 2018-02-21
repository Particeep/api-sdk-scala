package com.particeep.api

import com.particeep.api.core._
import com.particeep.api.models.ErrorResult
import com.particeep.api.models.scoring_metrics._
import play.api.libs.json.JsObject

import scala.concurrent.{ ExecutionContext, Future }

trait ScoringMetricsCapability {
  self: WSClient =>

  val scoring_metric: ScoringMetricClient = new ScoringMetricClient(this)

  def scoring_metric(credentials: ApiCredential): ScoringMetricClient = new ScoringMetricClient(this, Some(credentials))
}

object ScoringMetricClient {
  private val endPoint: String = "/scoring-metrics"
  private implicit val scoring_evaluation_format = ScoringEvaluation.format
  private implicit val scoring_metric_format = ScoringMetric.format
}

class ScoringMetricClient(val ws: WSClient, val credentials: Option[ApiCredential] = None) extends WithWS with WithCredentials with EntityClient {

  import ScoringMetricClient._

  def runScoringEvaluation(
    input_from_form: JsObject,
    metric_id:       String,
    timeout:         Long     = -1
  )(implicit exec: ExecutionContext): Future[Either[ErrorResult, ScoringEvaluation]] = {
    ws.post[ScoringEvaluation](s"$endPoint/evals", timeout, input_from_form, List(("metric_id", metric_id)))
  }

  def listMetrics(timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Seq[ScoringMetric]]] = {
    ws.get[Seq[ScoringMetric]](s"$endPoint/metrics/", timeout)
  }

  def byId(metric_id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, ScoringMetric]] = {
    ws.get[ScoringMetric](s"$endPoint/metrics/$metric_id", timeout)
  }
}
