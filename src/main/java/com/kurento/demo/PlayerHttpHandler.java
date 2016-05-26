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

/**
 * HTTP Player Handler which plays a collection of videos depending on the
 * <code>contentId</code> of the request; using redirect strategy; without JSON
 * signalling protocol.
 * 
 * @author Luis López (llopez@gsyc.es)
 * @author Boni García (bgarcia@gsyc.es)
 * @since 1.0.0
 * @see VideoURLs
 */
@HttpPlayerService(name = "PlayerHttpHandler", path = "/playerHttp/*", redirect = true, useControlProtocol = false)
public class PlayerHttpHandler extends HttpPlayerHandler {

	@Override
	public void onContentRequest(HttpPlayerSession session) throws Exception {
		// MP4 video by default
		String url = VideoURLs.map.get("mp4");

		// The URL or the video to be played is selected using the value of
		// contentId
		String contentId = session.getContentId();
		if (contentId != null && VideoURLs.map.containsKey(contentId)) {
			url = VideoURLs.map.get(contentId);
		}
		getLogger().info("Playing " + url);
		session.start(url);
	}

}
