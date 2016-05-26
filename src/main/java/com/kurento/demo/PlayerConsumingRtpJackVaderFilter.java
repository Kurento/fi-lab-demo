/*
 * (C) Copyright 2013 Kurento (http://kurento.org/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.kurento.demo;

import com.kurento.kmf.content.HttpPlayerHandler;
import com.kurento.kmf.content.HttpPlayerService;
import com.kurento.kmf.content.HttpPlayerSession;
import com.kurento.kmf.media.HttpGetEndpoint;

/**
 * HTTP Player Handler which plays a RTP stream previously produced in
 * {@link RtpProducingJackVaderFilter} handler; using tunnelling strategy (by
 * default <code>redirect=true</code> in {@link HttpPlayerService} annotation;
 * using JSON signalling protocol.
 * 
 * @author Luis López (llopez@gsyc.es)
 * @author Boni García (bgarcia@gsyc.es)
 * @since 1.0.0
 * @see RtpProducingJackVaderFilter
 */
@HttpPlayerService(name = "PlayerConsumingRtpJackVaderFilter", path = "/playerRtpJack", useControlProtocol = true)
public class PlayerConsumingRtpJackVaderFilter extends HttpPlayerHandler {

	@Override
	public void onContentRequest(HttpPlayerSession session) throws Exception {
		getLogger().info("Received request to " + session);
		if (RtpProducingJackVaderFilter.sharedJackVaderReference != null) {
			getLogger()
					.info("Found sharedJackVaderReference ... invoking play");
			HttpGetEndpoint httpEP = session.getMediaPipelineFactory().create()
					.newHttpGetEndpoint().terminateOnEOS().build();
			RtpProducingJackVaderFilter.sharedJackVaderReference
					.connect(httpEP);
			session.start(httpEP);
		} else {
			getLogger()
					.info("Cannot find sharedJackVaderReference instance ... rejecting request");
			session.terminate(500,
					"Cannot find sharedJackVaderReference instance ... rejecting request");
		}
	}
}
