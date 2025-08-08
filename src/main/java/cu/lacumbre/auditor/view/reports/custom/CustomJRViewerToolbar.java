package cu.lacumbre.auditor.view.reports.custom;

import cu.lacumbre.utils.Logger;
import java.awt.event.ActionEvent;
import net.sf.jasperreports.swing.JRViewerController;
import net.sf.jasperreports.swing.JRViewerToolbar;

public class CustomJRViewerToolbar extends JRViewerToolbar {

    public CustomJRViewerToolbar(JRViewerController viewerContext) {
        super( viewerContext);
        btnSave.addActionListener((ActionEvent e) -> {
            Logger.getInstance().updateInfoLog("Probando");
        });
    }
}
//    public CustomJRViewerToolbar(JRViewerController viewerContext) {
//        super(viewerContext);
//    }
//
//    public File getLastFolder() {
//        return lastFolder;
//    }
//    public void setLastFolder(File lastFolder) {
//        this.lastFolder = lastFolder;
//    }
//
//    public JButton getBtnSave() {
//        return btnSave;
//    }

