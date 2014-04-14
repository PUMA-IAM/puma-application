package puma.application.webapp.metrics;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import puma.util.timing.TimerFactory;

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
}