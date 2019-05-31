package com.zim.demo.docx4j;

import com.google.common.collect.Lists;
import java.io.File;
import java.io.OutputStream;
import java.util.List;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamSource;
import org.apache.fop.apps.FOUserAgent;
import org.docx4j.Docx4J;
import org.docx4j.TraversalUtil;
import org.docx4j.XmlUtils;
import org.docx4j.convert.out.FOSettings;
import org.docx4j.convert.out.fo.renderers.FORendererApacheFOP;
import org.docx4j.finders.CommentFinder;
import org.docx4j.fonts.IdentityPlusMapper;
import org.docx4j.fonts.Mapper;
import org.docx4j.fonts.PhysicalFont;
import org.docx4j.fonts.PhysicalFonts;
import org.docx4j.model.fields.FieldUpdater;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.org.apache.xml.serializer.utils.ResourceUtils;
import org.docx4j.wml.Body;
import org.docx4j.wml.ContentAccessor;
import org.docx4j.wml.Document;
import org.docx4j.wml.P;
import org.jvnet.jaxb2_commons.ppp.Child;

/**
 * @author zhenwei.liu
 * @since 2019-05-29
 */
public class Test {

	public static void main(String[] args) throws Exception {
//		String inPath = "/Users/liuzhenwei/Desktop/testword/Abc456.docx";
//		String outPath = "/Users/liuzhenwei/Desktop/testword/Abc456.pdf";
//		String outDocs = "/Users/liuzhenwei/Desktop/testword/Abc456xxx.docx";


		String inPath = "/Users/liuzhenwei/Desktop/word/final.docx";
		String outPath = "/Users/liuzhenwei/Desktop/word/final.pdf";
//		String outDocs = "/Users/liuzhenwei/Desktop/word/testa2.docx";

		WordprocessingMLPackage word = Docx4J.load(new File(inPath));
		MainDocumentPart main = word.getMainDocumentPart();
		Document doc = main.getJaxbElement();
		Body body = doc.getBody();

		// TODO 1 delete comment
//		CommentFinder cf = new CommentFinder();
//		new TraversalUtil(body, cf);
//
//		for (Child commentElement : cf.getCommentElements()) {
//			System.out.println(commentElement.getClass().getName());
//			Object parent = commentElement.getParent();
//			List<Object> theList = ((ContentAccessor) parent).getContent();
//			boolean removeResult = remove(theList, commentElement);
//			System.out.println(removeResult);
//		}
//
//		// 换行符删除特殊处理
////		ClassFinder classFinder = new ClassFinder(RunDel.class);
////		new TraversalUtil(body, classFinder);
//		List<Object> content = Lists.newArrayList(body.getContent());
//		for (Object o : content) {
//			System.out.println(o.getClass().getName());
//			if (o instanceof P) {
//				if (((P) o).getRsidDel() != null) {
//					boolean removeResult = remove(body.getContent(), o);
//					System.out.println(removeResult);
//				}
//			}
//		}
//
//		// TODO 2 apply tracking changes
//		// Load the XLST
//		Source xsltSource = new StreamSource(
//				ResourceUtils.getResource(
//						"AcceptChanges.xslt")
//		);
//		Templates xslt = XmlUtils.getTransformerTemplate(xsltSource);
//
//		DOMResult contentAccepted = new DOMResult();
//
//		// perform the transformation
//		main.transform(xslt, null, contentAccepted);
//
//		// replace the contents in the WordprocessingMLPackage
//		org.w3c.dom.Document domDoc = (org.w3c.dom.Document) contentAccepted.getNode();
//		main.setContents(
//				main.unmarshal(domDoc.getDocumentElement()));
////
////		// save docs
//		Docx4J.save(word, new File(outDocs), Docx4J.FLAG_NONE); //(FLAG_NONE == default == zipped docx)
//		System.out.println("Saved: " + outDocs);

		// TODO 3 convert to pdf

		// Font regex (optional)
		// Set regex if you want to restrict to some defined subset of fonts
		// Here we have to do this before calling createContent,
		// since that discovers fonts
		String regex = null;
		// Windows:
		// String
		// regex=".*(calibri|camb|cour|arial|symb|times|Times|zapf).*";
		//regex=".*(calibri|camb|cour|arial|times|comic|georgia|impact|LSANS|pala|tahoma|trebuc|verdana|symbol|webdings|wingding).*";
		// Mac
		// String
		// regex=".*(Courier New|Arial|Times New Roman|Comic Sans|Georgia|Impact|Lucida Console|Lucida Sans Unicode|Palatino Linotype|Tahoma|Trebuchet|Verdana|Symbol|Webdings|Wingdings|MS Sans Serif|MS Serif).*";
		PhysicalFonts.setRegex(regex);

		// Refresh the values of DOCPROPERTY fields
		FieldUpdater updater = null;
//		updater = new FieldUpdater(wordMLPackage);
//		updater.update(true);

		// Set up font mapper (optional)
		Mapper fontMapper = new IdentityPlusMapper();
//		Mapper fontMapper = new BestMatchingMapper();
		word.setFontMapper(fontMapper);

		// .. example of mapping font Times New Roman which doesn't have certain Arabic glyphs
		// eg Glyph "ي" (0x64a, afii57450) not available in font "TimesNewRomanPS-ItalicMT".
		// eg Glyph "ج" (0x62c, afii57420) not available in font "TimesNewRomanPS-ItalicMT".
		// to a font which does
		PhysicalFont font
				= PhysicalFonts.get("Arial Unicode MS");
		// make sure this is in your regex (if any)!!!
//		if (font!=null) {
//			fontMapper.put("Times New Roman", font);
//			fontMapper.put("Arial", font);
//		}
//		fontMapper.put("Libian SC Regular", PhysicalFonts.get("SimSun"));

		// FO exporter setup (required)
		// .. the FOSettings object
		FOSettings foSettings = Docx4J.createFOSettings();
//		if (saveFO) {
//			foSettings.setFoDumpFile(new java.io.File(inputfilepath + ".fo"));
//		}
		foSettings.setWmlPackage(word);

		FOUserAgent foUserAgent = FORendererApacheFOP.getFOUserAgent(foSettings);
		// configure foUserAgent as desired
		foUserAgent.setTitle("my title");

//	    foUserAgent.getRendererOptions().put("pdf-a-mode", "PDF/A-1b");
		// is easier than
//		foSettings.setApacheFopConfiguration(apacheFopConfiguration);

		// PDF/A-1a, PDF/A-2a and PDF/A-3a require accessibility to be enabled
		// see further https://stackoverflow.com/a/54587413/1031689
//	    foUserAgent.setAccessibility(true); // suppress "missing language information" messages from FOUserAgent .processEvent

		// Document format:
		// The default implementation of the FORenderer that uses Apache Fop will output
		// a PDF document if nothing is passed via
		// foSettings.setApacheFopMime(apacheFopMime)
		// apacheFopMime can be any of the output formats defined in org.apache.fop.apps.MimeConstants eg org.apache.fop.apps.MimeConstants.MIME_FOP_IF or
		// FOSettings.INTERNAL_FO_MIME if you want the fo document as the result.
		//foSettings.setApacheFopMime(FOSettings.INTERNAL_FO_MIME);

		// exporter writes to an OutputStream.
		OutputStream os = new java.io.FileOutputStream(outPath);

		// Specify whether PDF export uses XSLT or not to create the FO
		// (XSLT takes longer, but is more complete).

		// Don't care what type of exporter you use
		Docx4J.toFO(foSettings, os, Docx4J.FLAG_EXPORT_PREFER_XSL);

		// Prefer the exporter, that uses a xsl transformation
		// Docx4J.toFO(foSettings, os, Docx4J.FLAG_EXPORT_PREFER_XSL);

		// Prefer the exporter, that doesn't use a xsl transformation (= uses a visitor)
		// .. faster, but not yet at feature parity
		// Docx4J.toFO(foSettings, os, Docx4J.FLAG_EXPORT_PREFER_NONXSL);

		System.out.println("Saved: " + outPath);

		// Clean up, so any ObfuscatedFontPart temp files can be deleted
		if (word.getMainDocumentPart().getFontTablePart() != null) {
			word.getMainDocumentPart().getFontTablePart().deleteEmbeddedFontTempFiles();
		}

	}

	private static boolean remove(List<Object> theList, Object bm) {
		// Can't just remove the object from the parent,
		// since in the parent, it may be wrapped in a JAXBElement
		for (Object ox : theList) {
			if (XmlUtils.unwrap(ox).equals(bm)) {
				return theList.remove(ox);
			}
		}
		return false;
	}
}
