package com.example.service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import com.google.common.io.ByteStreams;

public class PdfGenerator {
    public static void generatePdf(String dest) throws IOException {
        // Initialize PDF writer
        PdfWriter writer = new PdfWriter(dest);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        // Add a paragraph
        document.add(new Paragraph("Hello, World! This is a PDF generated using iText 7 and Guava ByteStreams."));

        // Close document
        document.close();
    }

    public static void main(String[] args) {
        try {
            // Create a temporary file 
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            String pdfPath = "output.pdf";
            generatePdf(pdfPath);
            // To read as bytes using Guava
            byte[] pdfData = ByteStreams.toByteArray(baos);
            
            System.out.println("PDF generated at: " + pdfPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}