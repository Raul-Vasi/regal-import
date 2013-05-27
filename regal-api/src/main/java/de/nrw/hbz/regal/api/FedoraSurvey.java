/*
 * Copyright 2012 hbz NRW (http://www.hbz-nrw.de/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package de.nrw.hbz.regal.api;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jan Schnasse schnasse@hbz-nrw.de
 * 
 */
class FedoraSurvey
{
	final static Logger logger = LoggerFactory.getLogger(FedoraSurvey.class);
	String cacheDir = "/opt/edoweb/edobase/";
	String uriBase = "http://orthos.hbz-nrw.de/objects";

	Actions actions = null;

	FedoraSurvey() throws IOException
	{
		actions = new Actions();
	}

	List<View> survey()
	{
		System.out.println("Hole alle pids");
		List<String> pids = actions.getAll();
		Vector<View> rows = new Vector<View>();

		System.out.println("Hole infos für: " + pids.size() + " pids.");
		int count = 0;
		for (String pid : pids)
		{
			System.out.println("Pid: " + (++count));
			View view = actions.getView(pid);
			if (view != null)
				rows.add(view);
		}

		return rows;
	}
}