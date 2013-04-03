/*
 * Copyright (C) 2011 hangxin1940@gmail.com  http://hangxin1940.cnblogs.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.igeek.android.pluginframework.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.igeek.android.pluginframework.beans.Plugin;
import org.igeek.android.pluginframework.beans.PluginFeature;
import org.igeek.android.pluginframework.beans.PluginFeatureMethod;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;
import android.util.Xml;

/**
 * @author 作者 E-mail:hangxin1940@gmail.com
 * @version 创建时间：2011-12-15 上午11:58:36 解析xml
 */
public class XMLParse {
	private static final String _PLUGIN = "plugin-features";
	private static final String _FEATURE = "feature";
	private static final String _METHOD = "method";
	private static final String _DESCRIPTION = "description";
	private static final String _NEEDCTX = "need-context";

	private static final String _NAME = "name";

	private static final String _NAME_SPACE = "";

	public XMLParse() {
	}

	/**
	 * 解析xml组装纯类名文本描述的插件
	 * 
	 * @param ins
	 * @return
	 * @throws IOException
	 * @throws XmlPullParserException
	 */
	public Plugin parsePluginXML(InputStream ins) throws IOException,
			XmlPullParserException {
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(ins, "utf-8");

		Plugin plugin = new Plugin();

		List<PluginFeature> features = new ArrayList<PluginFeature>();

		int event = parser.getEventType();

		PluginFeature feature = new PluginFeature();

		PluginFeatureMethod featureMethod = new PluginFeatureMethod();

		// 一直走到文档结束
		while (event != XmlPullParser.END_DOCUMENT) {

			switch (event) {
			// 结点标签开始
			case XmlPullParser.START_TAG:

				// 主节点
				if (_PLUGIN.equals(parser.getName())) {
					Log.i("org.igeek.android-plugin",
							"plugin.xml document start parse");
				}

				// 功能描述结点
				else if (_DESCRIPTION.equals(parser.getName())) {
					plugin.setDescription(parser.getAttributeValue(_NAME_SPACE,
							_NAME));
				}

				// 功能描述结点
				else if (_FEATURE.equals(parser.getName())) {
					feature.setFeatureName(parser.getAttributeValue(
							_NAME_SPACE, _NAME));
				}

				// 方法描述结点
				else if (_METHOD.equals(parser.getName())) {

					featureMethod.setMethodName(parser.getAttributeValue(
							_NAME_SPACE, _NAME));
					if ("true".endsWith(parser.getAttributeValue(_NAME_SPACE,
							_NEEDCTX)))
						featureMethod.setNeedContext(true);
					else
						featureMethod.setNeedContext(false);

					featureMethod.setDescription(parser.nextText());
					event = parser.getEventType();
					if (event == XmlPullParser.END_TAG) {
						continue;
					}
				}
				break;

			// 节点表前结束
			case XmlPullParser.END_TAG:

				// 方法描述结点
				if (_METHOD.equals(parser.getName())) {
					feature.addMethod(featureMethod);
					featureMethod = new PluginFeatureMethod();
				}

				// 功能描述结点
				else if (_FEATURE.equals(parser.getName())) {
					features.add(feature);
					feature = new PluginFeature();
				}

				// 主节点
				else if (_PLUGIN.equals(parser.getName())) {
					Log.i("org.igeek.android-plugin",
							"plugin.xml document parsed ok");
				}

				break;
			}

			event = parser.next();
		}

		ins.close();

		plugin.setFeatures(features);
		return plugin;

	}

}
