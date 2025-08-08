package cu.lacumbre.auditor.view.reports.custom;

import net.sf.jasperreports.engine.JasperPrint;

import javax.swing.*;
import net.sf.jasperreports.view.JasperViewer;

public final class CustomJasperViewer extends JasperViewer {
    
    public CustomJasperViewer(JasperPrint jasperPrint, boolean isExitOnClose) {
        super(jasperPrint, isExitOnClose);
        this.viewer = new CustomJRViewer(jasperPrint);
        this.setTitle("Visor de informes de Auditor");
        this.setIconImage(new ImageIcon(getClass().getResource("/assets/icon.png")).getImage());
    }
    

//    private final CustomJRViewer customViewer;

//    public CustomJasperViewer(JasperReportsContext jasperReportsContext, JasperPrint jasperPrint, boolean isExitOnClose) {
//        super(jasperPrint, isExitOnClose);
//        customViewer = new CustomJRViewer(jasperReportsContext, jasperPrint, null, null);
//        this.viewer = customViewer;
//        ((CustomJRViewerToolbar) this.getViewer().getTlbToolBar()).setLastFolder(Auditor.getLastDirectoryOf(Auditor.REPORTS_DIRECTORY));
//    }
//
//    public CustomJRViewer getViewer() {
//        return customViewer;
//    }
//
//    @Override
//    public void dispose() {
//        Auditor.setLastDirectory(Auditor.REPORTS_DIRECTORY, customViewer.getCustomTlbToolBar().getLastFolder());
//        super.dispose(); 
//    }


//    private final CustomJRViewer customViewer;

//    public CustomJasperViewer(JasperReportsContext jasperReportsContext, JasperPrint jasperPrint, boolean isExitOnClose) {
//        super(jasperPrint, isExitOnClose);
//        customViewer = new CustomJRViewer(jasperReportsContext, jasperPrint, null, null);
//        this.viewer = customViewer;
//        ((CustomJRViewerToolbar) this.getViewer().getTlbToolBar()).setLastFolder(Auditor.getLastDirectoryOf(Auditor.REPORTS_DIRECTORY));
//        this.setTitle("Visor de informes de Auditor");
//        this.setIconImage(new ImageIcon(getClass().getResource("/assets/icon.png")).getImage());
//    }
//
//    public CustomJRViewer getViewer() {
//        return customViewer;
//    }
//
//    @Override
//    public void dispose() {
//        Auditor.setLastDirectory(Auditor.REPORTS_DIRECTORY, customViewer.getCustomTlbToolBar().getLastFolder());
//        super.dispose(); 
//    }

}
