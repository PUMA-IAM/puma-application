package puma.application.evaluation.metrics;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import puma.util.timing.TimerFactory;

import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.graphite.Graphite;
import com.codahale.metrics.graphite.GraphiteReporter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@Controller
public class MetricsController {

	@RequestMapping(value = "/metrics/results", method = RequestMethod.GET, produces="text/plain")
	public @ResponseBody String results() {
		try {
			ObjectMapper mapper = new ObjectMapper();
			ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
			return writer.writeValueAsString(TimerFactory.getInstance().getMetricRegistry());
		} catch (JsonProcessingException e) {
			return e.getMessage();
		}
	}
	
	@RequestMapping(value = "/metrics/clear", method = RequestMethod.GET)
	public @ResponseBody void clear() {
		TimerFactory.getInstance().resetAllTimers();
		
		// connect metrics to the Graphite server
		final Graphite graphite = new Graphite(new InetSocketAddress("172.16.4.2", 2003));
		final GraphiteReporter reporter = GraphiteReporter.forRegistry(TimerFactory.getInstance().getMetricRegistry())
		                                                  .prefixedWith("puma-application")
		                                                  .convertRatesTo(TimeUnit.SECONDS)
		                                                  .convertDurationsTo(TimeUnit.MILLISECONDS)
		                                                  .filter(MetricFilter.ALL)
		                                                  .build(graphite);
		reporter.start(10, TimeUnit.SECONDS);
	}
}