package kis.servlet;

import com.alibaba.fastjson.JSON;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import kis.dao.SaleOrderHome;
import kis.entity.SaleOrder;
import kis.entity.SaleOrderItem;
import kis.util.JSONResult;
import kis.util.Settings;
import kis.util.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by jim on 2015/8/7.
 */
@WebServlet(name = "SaleOrderPdfServlet", urlPatterns = "*.pdf")
public class SaleOrderPdfServlet extends HttpServlet {

    SaleOrderHome saleOrderHome = new SaleOrderHome();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String servletPath = request.getServletPath();
        System.out.println("servletPath:"+servletPath);
        if (!servletPath.startsWith("/sale/")) {
            dispatchNotFound(request, response);
            return;
        }
        int pos = servletPath.lastIndexOf("/");
        int posEnd = servletPath.lastIndexOf(".pdf");
        String orderNumber;
        if (pos != -1) {
            orderNumber = servletPath.substring(pos + 1, posEnd);
        } else {
            orderNumber = servletPath.substring(0, posEnd);

        }

        SaleOrder saleOrder = saleOrderHome.findByOrderNumber(orderNumber);
        if (saleOrder == null) {
            dispatchNotFound(request, response);
            return;
        }

        try {
            responseSaleOrderPdf(saleOrder, response);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    private void dispatchNotFound(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/include/not_found.jsp").forward(request,response);
    }

    public static void responseSaleOrderPdf(SaleOrder saleOrder, HttpServletResponse response) throws IOException, DocumentException {
        String orderNumber = saleOrder.getOrderNumber();
        response.setHeader("Content-Disposition", " inline; filename="+orderNumber+"\".pdf\"");
        response.setContentType("application/pdf");
        Document document = new Document();
        document.setPageSize(PageSize.A5.rotate()); // A5旋转90度变横向

        float width = PageSize.A5.rotate().getWidth();
        float height = PageSize.A5.rotate().getHeight();

        //document.setPageSize(new RectangleReadOnly(590.0F, 400.0F));
        document.setMargins(30, 15, 30, 15);
        PdfWriter.getInstance(document, response.getOutputStream());
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
        cell = new PdfPCell(new Phrase(saleOrder.getCompanyName(), font20));
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
        table.addCell(new Phrase("地址：" + saleOrder.getCompanyAddress(), font11));

        cell.setColspan(3);
        table.addCell(new Phrase("电话："+saleOrder.getCompanyContactNumber(), font11));
        table.addCell(new Phrase("传真：" + saleOrder.getCompanyFax(), font11));
        cell.setColspan(2);
        table.addCell(new Phrase("出货单编号："+saleOrder.getOrderNumber(), font11));

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
        table.addCell(new Phrase("客户名称：" + saleOrder.getCustomerName(), font11));

        cell.setColspan(3);
        table.addCell(new Phrase("客户电话："+saleOrder.getCustomerContactNumber(), font11));
        table.addCell(new Phrase("联系人：" + saleOrder.getCustomerPrincipal(), font11));
        cell.setColspan(2);
        table.addCell(new Phrase("", font11));

        cell.setColspan(4);
        table.addCell(new Phrase("送货地址：" + saleOrder.getCustomerAddress(), font11));
        cell.setColspan(3);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(new Phrase("送货日期：" + saleOrder.getDateText(), font11));
        cell.setColspan(1);
        //table.addCell(new Phrase("", font11));
        table.addCell((String)null);

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

        cell = new PdfPCell(new Phrase("备注", font11Bold));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);

        table.addCell(cell);

        cell = table.getDefaultCell();
        cell.setFixedHeight(20);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);


        ArrayList<SaleOrderItem> items = new ArrayList(saleOrder.getSaleOrderItems());
        int size = items.size();
        for (int i=0; i< Settings.SALE_ORDER_ITEM_NUM; i++) {
            String modalName = null;
            String categoryName = null;
            String units = null;
            String quantity = null;
            String unitPrice = null;
            String sum = null;
            String memo = null;
            if (i<size) {
                SaleOrderItem item = items.get(i);
                modalName = item.getProductModalText();        //
                categoryName = item.getProductCategoryText();  //
                units = item.getProductUnitsText();            //
                quantity = item.getQuantity()+"";
                unitPrice = WebUtils.nullToBlank(item.getUnitPrice());
                sum = WebUtils.nullToBlank(item.getSum());
                memo = item.getMemo();
            }
            //for (int i = 0; i<5; i++) {
            table.addCell(new Phrase(WebUtils.nullToBlank(modalName), font11));
            table.addCell(new Phrase(WebUtils.nullToBlank(categoryName), font11));
            table.addCell(new Phrase(WebUtils.nullToBlank(units), font11));
            table.addCell(new Phrase(WebUtils.nullToBlank(quantity), font11));
            table.addCell(new Phrase(WebUtils.nullToBlank(unitPrice), font11));
            table.addCell(new Phrase(WebUtils.nullToBlank(sum), font11));
            table.addCell(new Phrase(WebUtils.nullToBlank(memo), font11));

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


        cell = new PdfPCell(new Phrase("合计金额："+WebUtils.nullToBlank(saleOrder.getTotalSum()), font11Bold));
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

        document.add(table);
        document.close();

    }
}
