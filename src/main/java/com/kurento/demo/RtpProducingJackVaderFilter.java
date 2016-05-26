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

import com.kurento.kmf.content.RtpContentHandler;
import com.kurento.kmf.content.RtpContentService;
import com.kurento.kmf.content.RtpContentSession;
import com.kurento.kmf.media.JackVaderFilter;
import com.kurento.kmf.media.MediaPipeline;
import com.kurento.kmf.media.MediaPipelineFactory;
import com.kurento.kmf.media.RtpEndpoint;

/**
 * RTP Content Handler which produces a media pipeline composed by a
 * <code>JackVaderFilter</code>.
 * 
 * @author Luis López (llopez@gsyc.es)
 * @author Boni García (bgarcia@gsyc.es)
 * @since 1.0.0
 * @see PlayerConsumingRtpJackVaderFilter
 */
@RtpContentService(name = "RtpProducingJackVaderFilter", path = "/rtpJack")
public class RtpProducingJackVaderFilter extends RtpContentHandler {

	public static JackVaderFilter sharedJackVaderReference;

	@Override
	public void onContentRequest(RtpContentSession session) throws Exception {
		MediaPipelineFactory mpf = session.getMediaPipelineFactory();
		MediaPipeline mp = mpf.create();
		session.releaseOnTerminate(mp);
		JackVaderFilter filter = mp.newJackVaderFilter().build();
		RtpEndpoint rtpEP = mp.newRtpEndpoint().build();
		filter.connect(rtpEP);
		session.start(rtpEP);
		sharedJackVaderReference = filter;
	}

}
