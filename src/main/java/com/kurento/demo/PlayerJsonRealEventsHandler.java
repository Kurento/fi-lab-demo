/*
 * (C) Copyright 2016 Kurento (http://kurento.org/)
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

import com.kurento.kmf.content.ContentEvent;
import com.kurento.kmf.content.HttpPlayerHandler;
import com.kurento.kmf.content.HttpPlayerService;
import com.kurento.kmf.content.HttpPlayerSession;
import com.kurento.kmf.media.HttpGetEndpoint;
import com.kurento.kmf.media.MediaPipeline;
import com.kurento.kmf.media.MediaPipelineFactory;
import com.kurento.kmf.media.PlayerEndpoint;
import com.kurento.kmf.media.ZBarFilter;
import com.kurento.kmf.media.events.CodeFoundEvent;
import com.kurento.kmf.media.events.MediaEventListener;

/**
 * HTTP Player Handler which plays a media pipeline composed by a
 * <code>PlayerEndpoint</code> with a <code>ZBarFilter</code>; using redirect
 * strategy; with JSON signalling protocol.
 * 
 * @author Luis López (llopez@gsyc.es)
 * @author Boni García (bgarcia@gsyc.es)
 * @since 1.0.0
 */
@HttpPlayerService(name = "PlayerJsonRealEventsHandler", path = "/playerJsonEvents", redirect = true, useControlProtocol = true)
public class PlayerJsonRealEventsHandler extends HttpPlayerHandler {

	private String url = "";

	@Override
	public void onContentRequest(final HttpPlayerSession session)
			throws Exception {
		MediaPipelineFactory mpf = session.getMediaPipelineFactory();
		MediaPipeline mp = mpf.create();
		session.releaseOnTerminate(mp);

		PlayerEndpoint playerEndpoint = mp.newPlayerEndpoint(

		VideoURLs.map.get("zbar")).build();
		ZBarFilter filter = mp.newZBarFilter().build();
		playerEndpoint.connect(filter);

		filter.addCodeFoundListener(new MediaEventListener<CodeFoundEvent>() {

			@Override
			public void onEvent(CodeFoundEvent event) {
				getLogger().info("Event {}-->{}", event.getType(),
						event.getValue());
				if (url.equals(event.getValue())) {
					return;
				}

				url = event.getValue();
				session.publishEvent(new ContentEvent(event.getType(), event
						.getValue()));

			}
		});
		session.setAttribute("player", playerEndpoint);

		HttpGetEndpoint httpEP = mp.newHttpGetEndpoint().terminateOnEOS()
				.build();
		filter.connect(httpEP);
		session.start(httpEP);
	}

	@Override
	public void onContentStarted(HttpPlayerSession session) {
		PlayerEndpoint playerEndpoint = (PlayerEndpoint) session
				.getAttribute("player");
		playerEndpoint.play();
	}

}
