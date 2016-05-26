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

import java.util.HashMap;
import java.util.Map;

/**
 * Static class which contains a maps of the different URLs to be played in the
 * HTTP Player Handler of this project. For example, in
 * {@link PlayerHttpJsonHandler} and {@link PlayerHttpHandler} handlers, the
 * <code>contentId</code> of the request to this player handlers will be the key
 * in the map of this static class.
 * 
 * @author Boni García (bgarcia@gsyc.es)
 * @since 1.0.0
 */
public class VideoURLs {

	public static final Map<String, String> map;
	static {
		map = new HashMap<String, String>();
		map.put("webm", "file:///opt/video/sintel.webm");
		map.put("mov", "file:///opt/video/rabbit.mov");
		map.put("mkv", "file:///opt/video/fiware.mkv");
		map.put("3gp", "file:///opt/video/blackberry.3gp");
		map.put("ogv", "file:///opt/video/pacman.ogv");
		map.put("avi", "file:///opt/video/car.avi");
		map.put("mp4", "file:///opt/video/chrome.mp4");
		map.put("jack", "file:///opt/video/fiwarecut.webm");
		map.put("zbar", "file:///opt/video/barcodes.webm");
	};

}
