package kis.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.print.attribute.standard.PagesPerMinute;
import javax.swing.border.Border;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by jim on 2015/7/15.
 */
public class MyTest {

    public static void main(String arg[]) throws Throwable  {
        /*BigDecimal d = new BigDecimal("12.30");
        System.out.println(d);
        d = new BigDecimal(0);
        System.out.println(d);
        d = null;
        System.out.println(d);
        System.out.println("----");
        //d = new BigDecimal()

        long t1 = System.currentTimeMillis();
        new MyTest().writexlsx();
        long t2 = System.currentTimeMillis();
        System.out.println(t2-t1);//*/


       // new MyTest().pdf_table();
        //new MyTest().echoDate();

        String servletPath = "/12345.pdf";

        int pos = servletPath.lastIndexOf("/");
        int posEnd = servletPath.lastIndexOf(".pdf");
        String orderNumber_;
        if (pos != -1) {
            orderNumber_ = servletPath.substring(pos+1, posEnd);
        } else {
            orderNumber_ = servletPath.substring(0, posEnd);

        }
        System.out.println(orderNumber_);

    }

    public void pdf_table() throws IOException, DocumentException {
        Document document = new Document();
        document.setPageSize(PageSize.A5.rotate()); // A5旋转90度变横向

        float width = PageSize.A5.rotate().getWidth();
        float height = PageSize.A5.rotate().getHeight();

        //document.setPageSize(new RectangleReadOnly(590.0F, 400.0F));
        document.setMargins(30, 15, 30, 15);
        PdfWriter.getInstance(document, new FileOutputStream("C:\\Users\\jim\\Desktop\\info1.pdf"));
        BaseFont bf = BaseFont.createFont("C:/Windows/Fonts/simfang.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font font20 = new Font(bf, 20, Font.NORMAL);
        Font font16 = new Font(bf, 16, Font.NORMAL);
        Font font11 = new Font(bf, 11, Font.NORMAL);
        Font font11Bold = new Font(bf, 11, Font.BOLD);
        document.open();


        PdfPTable table = new PdfPTable(8);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{width*0.18f, width*0.15f, width*0.08f, width*0.1f, width*0.1f,width*0.14f, width*0.14f, width*0.11f});

        PdfPCell cell = table.getDefaultCell();


        //cell.addElement(new Phrase("苏州琴惠塑业有限公司", font18));
        cell = new PdfPCell(new Phrase("苏州琴惠塑业有限公司", font20));
        cell.setColspan(8);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(32);
        table.addCell(cell);

         //cell = table.getDefaultCell();
       // cell.addElement(new Phrase("成品出库单", font14));
        cell = new PdfPCell(new Phrase("成品出库单", font16));
        cell.setColspan(8);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setFixedHeight(22);
        table.addCell(cell);

        cell = table.getDefaultCell();
        cell.setColspan(8);
        cell.setFixedHeight(20);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(new Phrase("地址：苏州市相城区石星路", font11));

        cell.setColspan(3);
        table.addCell(new Phrase("电话：0512-65410295", font11));
        table.addCell(new Phrase("传真：0512-65410295", font11));
        cell.setColspan(2);
        table.addCell(new Phrase("出货单编号：10002834", font11));

        /*
        cell = new PdfPCell(new Phrase("地址：苏州市相城区石星路", font11));
        cell.setColspan(8);
        cell.setFixedHeight(20);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("电话：0512-65410295", font11));
        cell.setColspan(3);
        cell.setFixedHeight(20);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("传真：0512-65410295", font11));
        cell.setColspan(3);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("出货单编号：10002834", font11));
        cell.setColspan(2);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(cell);
        */

        cell= new PdfPCell();
        cell.setColspan(8);
        cell.setFixedHeight(4);
        cell.setBorderWidthTop(0.5f);
        cell.setBorderWidthLeft(0f);
        cell.setBorderWidthRight(0f);
        cell.setBorderWidthBottom(0f);
        table.addCell(cell);


        cell = table.getDefaultCell();
        cell.setFixedHeight(20);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setColspan(8);
        table.addCell(new Phrase("客户名称：江苏坤华汽车配件有限公司", font11));

        cell.setColspan(3);
        table.addCell(new Phrase("客户电话：0511-84948595", font11));
        table.addCell(new Phrase("联系人：陈玉坤", font11));
        cell.setColspan(2);
        table.addCell(new Phrase("", font11));

        cell.setColspan(4);
        table.addCell(new Phrase("送货地址：丹阳市XXX路", font11));
        cell.setColspan(3);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(new Phrase("送货日期：2015-07-01", font11));
        cell.setColspan(1);
        //table.addCell(new Phrase("", font11));
        table.addCell((String)null);
        /*
        cell = new PdfPCell(new Phrase("客户名称：江苏坤华汽车配件有限公司", font11));
        cell.setColspan(8);
        cell.setFixedHeight(20);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(cell);


        cell = new PdfPCell(new Phrase("客户电话：0511-84948595", font11));
        cell.setColspan(3);
        cell.setFixedHeight(20);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("联系人：陈玉坤", font11));
        cell.setColspan(3);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(cell);

        cell = new PdfPCell();
        cell.setColspan(2);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("送货地址：丹阳市XXX路", font11));
        cell.setColspan(4);
        cell.setFixedHeight(20);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("送货日期：2015-07-01", font11));
        cell.setColspan(3);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(cell);


        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(cell);
*/
        //规格型号	货物名称	单位	数量	价格	金额	说明	备注

        cell = table.getDefaultCell();
        cell.setColspan(1);
        cell.setBorder(Rectangle.BOX);
        cell.setBorderWidth(0.1f);
        cell.setFixedHeight(20);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(new Phrase("规格型号", font11Bold));
        table.addCell(new Phrase("货物名称", font11Bold));
        table.addCell(new Phrase("单位", font11Bold));
        table.addCell(new Phrase("数量", font11Bold));
        table.addCell(new Phrase("价格", font11Bold));
        table.addCell(new Phrase("金额", font11Bold));
        table.addCell(new Phrase("说明", font11Bold));

        /*cell = new PdfPCell(new Phrase("规格型号", font11Bold));
        cell.setFixedHeight(20);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("货物名称", font11Bold));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("单位", font11Bold));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("数量", font11Bold));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("价格", font11Bold));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("金额", font11Bold));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("说明", font11Bold));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        */

        cell = new PdfPCell(new Phrase("备注", font11Bold));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);

        table.addCell(cell);

        cell = table.getDefaultCell();
        cell.setFixedHeight(20);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);

        for (int i = 0; i<5; i++) {
            table.addCell(new Phrase("PP普黑料 A级", font11));
            table.addCell(new Phrase("聚丙烯", font11));
            table.addCell(new Phrase("千克", font11));
            table.addCell(new Phrase("1000", font11));
            table.addCell(new Phrase("7.2", font11));
            table.addCell(new Phrase("72000", font11));
            table.addCell(new Phrase("400包*25kg", font11));
            /*cell = new PdfPCell(new Phrase("PP普黑料 A级", font11));
            cell.setFixedHeight(20);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("聚丙烯", font11));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("千克", font11));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("10000", font11));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("7.2", font11));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("72000", font11));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("400包*25kg", font11));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);*/

            if (i==0) {
                cell = new PdfPCell(new Phrase("白联:留存\n" +
                        "红联:客户\n" +
                        "黄联:结算", font11));
                cell.setBorder(Rectangle.NO_BORDER);
                cell.setRowspan(7);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPaddingBottom(40);
                table.addCell(cell);
            }

        }



        cell = new PdfPCell(new Phrase("合计金额：2333333", font11Bold));
        cell.setColspan(7);
        cell.setBorderWidth(0.1f);
        cell.setFixedHeight(20);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setPaddingRight(width * 0.2f);
        table.addCell(cell);

        cell = table.getDefaultCell();
        cell.setColspan(2);
        cell.setFixedHeight(40);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(new Phrase("财务", font11));
        cell.setColspan(3);
        table.addCell(new Phrase("制单", font11));
        cell.setColspan(2);
        table.addCell(new Phrase("客户签收", font11));

        /*
        cell = new PdfPCell(new Phrase("财务", font11));
        cell.setColspan(2);
        cell.setFixedHeight(40);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("制单", font11));
        cell.setColspan(3);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("客户签收", font11));
        cell.setColspan(2);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

*/
        document.add(table);
        document.close();

    }






}
