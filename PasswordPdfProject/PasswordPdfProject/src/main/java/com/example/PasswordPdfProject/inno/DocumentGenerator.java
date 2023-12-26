package com.inno;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import java.io.*;

@Service
public class DocumentGenerator {

	private Logger log = LoggerFactory.getLogger(DocumentGenerator.class);

	public void htmlStringToPdf(String content) throws DocumentException, FileNotFoundException {
		Document document = new Document();
		try {
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("C:\\Users\\Muskan\\PDFwithPasswordOne1.pdf"));
			writer.setEncryption("concretepage".getBytes(), "cp123".getBytes(), PdfWriter.ALLOW_COPY, PdfWriter.STANDARD_ENCRYPTION_40);

			document.open();

			// Specify the path to your HTML file
			String htmlFilePath = "C:\\Users\\Muskan\\SpringBoottTymeleafExample\\target\\classes\\templates\\emp.html";

			// Read HTML content from the file
			String htmlContent = content;//readHtmlFile(htmlFilePath);

			// Use XMLWorkerHelper to parse HTML and add it to the PDF document
				XMLWorkerHelper.getInstance().parseXHtml(writer, document, new StringReader(htmlContent));

			document.close();
			System.out.println("Done");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String readHtmlFile(String filePath) {
		StringBuilder content = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = br.readLine()) != null) {
				content.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return content.toString();
	}
	}

