package com.example.dukandar20.Printer;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.example.dukandar20.models.ReceiptItem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class PDFGenerator {

    private Context context;

    public PDFGenerator(Context context) {
        this.context = context;
    }

    public void generatePDF(List<ReceiptItem> items, double totalAmount) {
        Document document = new Document();
        try {
            // Define the path and file name for the PDF
            String filePath = context.getExternalFilesDir(null) + "/Receipt_" + System.currentTimeMillis() + ".pdf";

            PdfWriter.getInstance(document, new FileOutputStream(filePath));

            document.open();

            // Add content to the PDF
            document.add(new Paragraph("Receipt Preview"));
            document.add(new Paragraph("Store Name"));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                document.add(new Paragraph("Date: " + LocalDate.now()));
            }
            document.add(new Paragraph("\n"));

            // Add table header
            document.add(new Paragraph("Item Name\tRate\tQty\tPrice"));
            document.add(new Paragraph("-----------------------------"));

            // Add receipt items
            for (ReceiptItem item : items) {
                document.add(new Paragraph(item.getName() + "\t₹" + String.format("%.2f", item.getPrice() / item.getQuantity()) + "\t" +
                        item.getQuantity() + "\t₹" + String.format("%.2f", item.getPrice())));
            }

            // Add total amount
            document.add(new Paragraph("\nTotal: ₹" + String.format("%.2f", totalAmount)));
            document.add(new Paragraph("\nThank you for shopping with us!"));

            document.close();

            Toast.makeText(context, "PDF generated successfully: " + filePath, Toast.LENGTH_LONG).show();

        } catch (DocumentException | IOException e) {
            Log.e("PDFGenerator", "Error generating PDF", e);
            Toast.makeText(context, "Error generating PDF", Toast.LENGTH_SHORT).show();
        }
    }
}
