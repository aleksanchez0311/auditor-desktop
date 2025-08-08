package cu.lacumbre.auditor.view.reports.custom;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;

public class CustomJRViewer extends JRViewer {
    

    public CustomJRViewer(JasperPrint jasperPrint) {
        super(jasperPrint);
        tlbToolBar = new CustomJRViewerToolbar(this.viewerContext);
    }
}




//    private final CustomJRViewerToolbar customJRViewerToolbar;
//
//    public CustomJRViewer(JasperReportsContext jasperReportsContext, JasperPrint jrPrint, Locale locale, ResourceBundle resBundle) {
//        super(jasperReportsContext, jrPrint, locale, resBundle);
//        customJRViewerToolbar = new CustomJRViewerToolbar(new JRViewerController(jasperReportsContext, null, null));
//        this.tlbToolBar = customJRViewerToolbar;
//    }
//
//    public CustomJRViewerToolbar getCustomTlbToolBar() {
//        return customJRViewerToolbar;
//    }
//    public JRViewerToolbar getTlbToolBar() {
//        return tlbToolBar;
//    }

