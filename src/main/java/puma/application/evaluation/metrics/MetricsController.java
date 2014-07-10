/*******************************************************************************
 * Copyright 2014 KU Leuven Research and Developement - iMinds - Distrinet 
 * 
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 * 
 *        http://www.apache.org/licenses/LICENSE-2.0
 * 
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *    
 *    Administrative Contact: dnet-project-office@cs.kuleuven.be
 *    Technical Contact: maarten.decat@cs.kuleuven.be
 *    Author: maarten.decat@cs.kuleuven.be
 ******************************************************************************/
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
	
	GraphiteReporter reporter = null;
	
	@RequestMapping(value = "/metrics/clear", method = RequestMethod.GET)
	public @ResponseBody void clear() {
		TimerFactory.getInstance().resetAllTimers();
		
		// connect metrics to the Graphite server
		if(reporter != null) {
			reporter.stop();
		}
		final Graphite graphite = new Graphite(new InetSocketAddress("172.16.4.2", 2003));
		reporter = GraphiteReporter.forRegistry(TimerFactory.getInstance().getMetricRegistry())
		                                                  .prefixedWith("puma-application")
		                                                  .convertRatesTo(TimeUnit.SECONDS)
		                                                  .convertDurationsTo(TimeUnit.MILLISECONDS)
		                                                  .filter(MetricFilter.ALL)
		                                                  .build(graphite);
		reporter.start(10, TimeUnit.SECONDS);
	}
}
