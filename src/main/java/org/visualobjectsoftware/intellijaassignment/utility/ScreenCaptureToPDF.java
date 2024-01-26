package org.visualobjectsoftware.intellijaassignment.utility;

import javafx.geometry.Rectangle2D;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;


public class ScreenCaptureToPDF {

    private static Rectangle convertToAWTRectangle(Rectangle2D rectangle2D) {
        return new Rectangle(
                (int) rectangle2D.getMinX(),
                (int) rectangle2D.getMinY(),
                (int) rectangle2D.getWidth(),
                (int) rectangle2D.getHeight()
        );
    }
    public static BufferedImage captureScreenArea(Rectangle2D bounds) throws Exception {
        Robot robot = new Robot();
        Rectangle captureRect = convertToAWTRectangle(bounds);
        return robot.createScreenCapture(captureRect);
    }


    public static void saveAsPdf(BufferedImage image, String filePath) throws IOException {
        try (PDDocument doc = new PDDocument()) {
            PDPage page = new PDPage();
            doc.addPage(page);

            PDImageXObject pdImage = LosslessFactory.createFromImage(doc, image);

            try (PDPageContentStream contentStream = new PDPageContentStream(doc, page)) {
                contentStream.drawImage(pdImage, 0, 0, pdImage.getWidth(), pdImage.getHeight());
            }

            doc.save(filePath);
        }
    }
}
