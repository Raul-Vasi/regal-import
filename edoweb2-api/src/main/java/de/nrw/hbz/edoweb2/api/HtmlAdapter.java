package de.nrw.hbz.edoweb2.api;

import java.io.StringWriter;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

public class HtmlAdapter
{

	public static String getHtml(View view)
	{

		String fedoraLogo = "http://www.fedora-commons.org/site-images/logo_vertical_white_200_251";
		String edowebStyle = "http://orthos.hbz-nrw.de/style.css";
		String edowebLogo = "http://orthos.hbz-nrw.de/logo.gif";

		try
		{
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder parser;

			parser = factory.newDocumentBuilder();
			Document doc = parser.newDocument();
			Element html = doc.createElement("html");
			html.setAttribute("xmlns", "http://www.w3.org/1999/xhtml");
			html.setAttribute("xml:lang", "de");
			Element head = doc.createElement("head");
			Element css = doc.createElement("link");

			css.setAttribute("rel", "stylesheet");
			css.setAttribute("href", edowebStyle);
			css.setAttribute("type", "text/css");
			head.appendChild(css);
			Element body = doc.createElement("body");

			Element div = doc.createElement("div");
			div.setAttribute("class", "logo");
			Element image = doc.createElement("image");
			image.setAttribute("src", edowebLogo);
			div.appendChild(image);
			body.appendChild(div);

			Element ul = doc.createElement("ul");
			ul.setAttribute("class", "collection");
			body.appendChild(ul);

			setItem(ul, doc, view);

			html.appendChild(head);
			html.appendChild(body);
			doc.appendChild(html);

			TransformerFactory tfact = TransformerFactory.newInstance();
			Transformer transformer = tfact.newTransformer();
			StringWriter writer = new StringWriter();
			Result result = new StreamResult(writer);
			Source source = new DOMSource(doc);
			transformer.transform(source, result);
			writer.flush();
			writer.close();
			String xml = writer.toString();

			return xml;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}

	private static void setItem(Element ul, Document doc, View view)
	{
		Element li = doc.createElement("li");
		li.setAttribute("class", "item");

		Element table = doc.createElement("table");

		Element tr = doc.createElement("tr");
		Element td = doc.createElement("td");
		td.setAttribute("class", "rlabel");
		td.setAttribute("colspan", "3");
		Element a = doc.createElement("a");
		a.setAttribute("href", view.getUri());
		Text text = doc.createTextNode(view.getFirstAlephId());
		a.appendChild(text);
		td.appendChild(a);
		tr.appendChild(td);
		table.appendChild(tr);

		tr = doc.createElement("tr");
		Element td1 = doc.createElement("td");
		// td1.setAttribute("class", "thumbnail");
		// td1.setAttribute("rowspan", "15");
		//
		// Element fulltextLink = doc.createElement("a");
		// fulltextLink.setAttribute("href", view.getFirstPdfUrl());
		//
		// Element thumb = doc.createElement("image");
		// thumb.setAttribute("width", "128");
		// thumb.setAttribute("height", "*");
		// thumb.setAttribute("alt", "Fulltext");
		// thumb.setAttribute("src", view.getFirstThumbnailUrl());
		//
		// fulltextLink.appendChild(thumb);
		//
		// td1.appendChild(fulltextLink);

		tr.appendChild(td1);

		table.appendChild(tr);

		addToTable(doc, table, "Title", view.getTitle());

		addToTable(doc, table, "Creator", view.getCreator());

		addToTable(doc, table, "Year", view.getYear());

		addToTable(doc, table, "Type", view.getType());

		addToTable(doc, table, "Publisher", view.getPublisher());

		addToTable(doc, table, "Language", view.getLanguage());

		addToTable(doc, table, "Location", view.getLocation());

		addToTable(doc, table, "Subject", view.getSubject());

		addToTable(doc, table, "DDC", view.getDdc());

		addToTable(doc, table, "DOI", view.getDoi());

		addToTable(doc, table, "URN", view.getUrn());

		addToTable(doc, table, "Related Url", view.getUrl());

		addToTable(doc, table, "hasPart", view.getHasPart());

		addToTable(doc, table, "IsPartOf", view.getIsPartOf());

		tr = doc.createElement("tr");
		td1 = doc.createElement("td");
		Element td0 = doc.createElement("td");

		td0.setAttribute("class", "plabel");
		td0.appendChild(doc.createTextNode("External"));

		a = doc.createElement("a");
		a.setAttribute("href", view.getFirstLobidUrl());
		a.appendChild(doc.createTextNode("@ lobid.org"));
		td1.appendChild(a);

		a = doc.createElement("a");
		a.setAttribute("href", view.getFirstVerbundUrl());
		a.appendChild(doc.createTextNode("@ hbz-nrw.de"));
		td1.appendChild(a);

		tr.appendChild(td1);

		table.appendChild(tr);

		table.appendChild(tr);

		tr = doc.createElement("tr");
		td0 = doc.createElement("td");
		td1 = doc.createElement("td");
		Element image = doc.createElement("img");

		td0.setAttribute("class", "editIcon");
		// a = doc.createElement("a");
		// a.setAttribute("href", pideditor);
		// image = doc.createElement("img");
		// image.setAttribute("src", editIcon);
		// a.appendChild(image);
		// td0.appendChild(a);
		tr.appendChild(td0);

		td1.setAttribute("class", "fedoraLogo");
		// a = doc.createElement("a");
		// a.setAttribute("href", view.getFirstObjectUrl());
		// image = doc.createElement("img");
		// image.setAttribute("src", this.fedoraLogo);
		// a.appendChild(image);
		// td1.appendChild(a);
		tr.appendChild(td1);

		table.appendChild(tr);

		li.appendChild(table);

		ul.appendChild(li);

	}

	static void addToTable(Document doc, Element table, String fieldName,
			Vector<String> values)
	{
		String urnResolver = "http://nbn-resolving.de/";
		String doiResolver = "http://dx.doi.org/";
		if (fieldName == "DOI")
		{

			for (String str : values)
			{
				Element tr = doc.createElement("tr");
				Element td1 = doc.createElement("td");
				td1.setAttribute("class", "plabel");
				Element td2 = doc.createElement("td");

				td1.appendChild(doc.createTextNode(fieldName));

				Element resolver = doc.createElement("a");
				resolver.setAttribute("href", doiResolver + str);
				resolver.appendChild(doc.createTextNode(str));
				td2.appendChild(resolver);

				tr.appendChild(td1);
				tr.appendChild(td2);
				table.appendChild(tr);
			}

		}
		else if (fieldName == "URN")
		{

			for (String str : values)
			{
				Element tr = doc.createElement("tr");
				Element td1 = doc.createElement("td");
				td1.setAttribute("class", "plabel");
				Element td2 = doc.createElement("td");

				td1.appendChild(doc.createTextNode(fieldName));

				Element resolver = doc.createElement("a");
				resolver.setAttribute("href", urnResolver + str);
				resolver.appendChild(doc.createTextNode(str));
				td2.appendChild(resolver);

				tr.appendChild(td1);
				tr.appendChild(td2);
				table.appendChild(tr);
			}
		}
		else
		{
			for (String str : values)
			{
				Element tr = doc.createElement("tr");
				Element td1 = doc.createElement("td");
				td1.setAttribute("class", "plabel");
				Element td2 = doc.createElement("td");

				td1.appendChild(doc.createTextNode(fieldName));

				if (str.startsWith("http"))
				{
					Element link = doc.createElement("a");
					link.setAttribute("href", str);
					link.appendChild(doc.createTextNode(str));
					td2.appendChild(link);
				}
				else
				{
					td2.appendChild(doc.createTextNode(str));
				}

				tr.appendChild(td1);
				tr.appendChild(td2);
				table.appendChild(tr);
			}
		}
	}

}
