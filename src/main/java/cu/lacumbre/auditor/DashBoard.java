package cu.lacumbre.auditor;

import cu.lacumbre.auditor.crud.DaysController;
import cu.lacumbre.auditor.crud.EntitiesCRUD;
import cu.lacumbre.auditor.crud.ItemsCRUD;
import cu.lacumbre.auditor.crud.OperationsCRUD;
import cu.lacumbre.auditor.crud.PostgreSQL;
import cu.lacumbre.auditor.crud.WorkersCRUD;
import cu.lacumbre.auditor.utils.ui.DashboardView;
import cu.lacumbre.auditor.utils.ui.ModernContentPanel;
import cu.lacumbre.auditor.utils.ui.ModernSidePanel;
import cu.lacumbre.auditor.utils.ui.ModernThemeManager;
import cu.lacumbre.auditor.view.inventory.AdjustNotSoldProducts;
import cu.lacumbre.auditor.view.inventory.Inventory;
import cu.lacumbre.auditor.view.inventory.LoadCSV;
import cu.lacumbre.auditor.view.inventory.MakeSale;
import cu.lacumbre.auditor.view.inventory.MapperGestion;
import cu.lacumbre.auditor.view.inventory.SaleFromDate;
import cu.lacumbre.auditor.view.merchandise.CostSheetsGestion;
import cu.lacumbre.auditor.view.merchandise.ExpensesGestion;
import cu.lacumbre.auditor.view.merchandise.MeasureUnitsGestion;
import cu.lacumbre.auditor.view.merchandise.ProductsGestion;
import cu.lacumbre.auditor.view.merchandise.RawMaterialsGestion;
import cu.lacumbre.auditor.view.merchandise.TPVCategoriesGestion;
import cu.lacumbre.auditor.view.merchandise.WorkablesGestion;
import cu.lacumbre.auditor.view.operations.ExpensesOperGestion;
import cu.lacumbre.auditor.view.operations.IncomesOperGestion;
import cu.lacumbre.auditor.view.operations.OutcomesOperGestion;
import cu.lacumbre.auditor.view.operations.SaleOperGestion;
import cu.lacumbre.auditor.view.operations.TransferOperGestion;
import cu.lacumbre.auditor.view.reports.DialogReportsProducts;
import cu.lacumbre.auditor.view.reports.DialogReportsRawMaterials;
import cu.lacumbre.auditor.view.reports.R_PeriodIncomes;
import cu.lacumbre.auditor.view.reports.R_PeriodMovements;
import cu.lacumbre.auditor.view.reports.R_PeriodOutcomes;
import cu.lacumbre.auditor.view.reports.R_PeriodSales;
import cu.lacumbre.auditor.view.settings.SettingsDialog;
import cu.lacumbre.auditor.view.utils.CreateCuadreFile;
import cu.lacumbre.auditor.view.utils.CustomComparator;
import cu.lacumbre.auditor.view.workers.DocumentsGestion;
import cu.lacumbre.auditor.view.workers.RolesGestion;
import cu.lacumbre.auditor.view.workers.StaffGestion;
import cu.lacumbre.utils.Logger;
import cu.lacumbre.utils.Settings;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Properties;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

public class DashBoard extends JFrame {

    private ModernSidePanel modernSidePanel;
    private ModernContentPanel modernContentPanel;
    private DashboardView dashboardView;
    private CardLayout mainCardLayout;
    private JPanel mainPanel;

    private PostgreSQL postgreSQL;
    private Connection connection;
    private EntitiesCRUD entitiesCRUD;
    private OperationsCRUD operationsCRUD;
    private ItemsCRUD itemsCRUD;
    private WorkersCRUD workersCRUD;
    private DaysController daysController;
    private CustomComparator customComparator;

    public DashBoard(PostgreSQL postgreSQL, EntitiesCRUD entitiesCRUD) throws IOException {
        try {
            this.postgreSQL = postgreSQL;
            this.connection = postgreSQL.getConnection();
            Properties options = Setup.options;
            int width = Integer.parseInt(options.getProperty("frameWidth"));
            int height = Integer.parseInt(options.getProperty("frameHeight"));;
            int extendedState = Integer.parseInt(options.getProperty("extendedState"));
            this.customComparator = new CustomComparator(connection, options.getProperty("comparator"));
            this.entitiesCRUD = entitiesCRUD;
            this.workersCRUD = new WorkersCRUD(connection);
            this.itemsCRUD = new ItemsCRUD<>(connection, EntitySelector.currentEntity, customComparator);
            this.operationsCRUD = new OperationsCRUD(connection, EntitySelector.currentEntity, itemsCRUD);
            this.daysController = new DaysController(connection, entitiesCRUD);
            ModernThemeManager.setupModernTheme();

            // Inicializar los componentes modernos
            modernSidePanel = new ModernSidePanel();
            modernContentPanel = new ModernContentPanel();

            // Configurar el layout principal
            getContentPane().setLayout(new BorderLayout());

            // Agregar los componentes principales
            getContentPane().add(modernSidePanel, BorderLayout.WEST);
            getContentPane().add(modernContentPanel, BorderLayout.CENTER);

            // Configurar el panel de contenido moderno
            modernContentPanel.setTitle("Auditor Desktop");

            // Configurar los menús del panel lateral
            setupModernMenus();

            // Panel de contenido moderno con dashboard
            dashboardView = new DashboardView();
            modernContentPanel = new ModernContentPanel();
            modernContentPanel.setTitle("Bienvenido a Auditor");
            modernContentPanel.setContent(dashboardView);

            // Configuración básica de la ventana
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            setTitle("Auditor para " + Setup.enterpriseName + " en " + EntitySelector.currentEntity.getDescription());
            setMinimumSize(new Dimension(1200, 700));

            // Crear estructura principal
            Container contentPane = getContentPane();
            contentPane.setLayout(new BorderLayout());

            // Agregar paneles principales
            contentPane.add(modernSidePanel, BorderLayout.WEST);
            contentPane.add(modernContentPanel, BorderLayout.CENTER);

            // Configurar menú lateral
            // setupSideMenu(); // Reemplazado por setupModernMenus()
            // Agregar acciones rápidas al panel de contenido
            setupQuickActions();

            // Configurar estado inicial
            initComponents();
            setSize(width, height);
            setExtendedState(extendedState);
            setIconImage(new ImageIcon(getClass().getResource("/assets/icon.png")).getImage());
            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent evt) {
                    try {
                        saveProperties(options);
                        postgreSQL.disconnect(new Exception().getStackTrace());
                    } catch (SQLException ex) {
                        Logger.getInstance().updateErrorLog(ex);
                    } finally {
                        super.windowClosing(evt);
                    }
                }
            });
        } catch (SQLException ex) {
            Logger.getInstance().updateErrorLog(ex);
            System.exit(1);
        }
    }

    public ItemsCRUD getItemsCRUD() {
        return itemsCRUD;
    }

    public void setItemsCRUD(ItemsCRUD itemsCRUD) {
        this.itemsCRUD = itemsCRUD;
    }

    public void setCustomComparator(CustomComparator customComparator) {
        this.customComparator = customComparator;
    }

    private void setupModernMenus() {
        // Agregar elementos al menú moderno
        JButton btnMerchandise = modernSidePanel.addMenuItem("Mercancía", "icons/merchandise.png");
        btnMerchandise.addActionListener(e -> {
            miMeasureUnitsActionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, ""));
        });

        JButton btnInventory = modernSidePanel.addMenuItem("Inventario", "icons/inventory.png");
        btnInventory.addActionListener(e -> {
            miInventorySumaryActionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, ""));
        });

        JButton btnWorkers = modernSidePanel.addMenuItem("Trabajadores", "icons/workers.png");
        btnWorkers.addActionListener(e -> {
            miStaffActionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, ""));
        });

        JButton btnSales = modernSidePanel.addMenuItem("Ventas", "icons/sales.png");
        btnSales.addActionListener(e -> {
            miSaleActionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, ""));
        });

        JButton btnReports = modernSidePanel.addMenuItem("Reportes", "icons/reports.png");
        btnReports.addActionListener(e -> {
            miPeriodSaleViewActionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, ""));
        });

        JButton btnSettings = modernSidePanel.addMenuItem("Configuración", "icons/settings.png");
        btnSettings.addActionListener(e -> {
            miSettingsActionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, ""));
        });
    }

    @Override
    public void dispose() {
        saveProperties(Setup.options);
        super.dispose();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sideMenu = new JPanel();
        panelMenu = new JPanel();
        jPanel12 = new JPanel();
        jPanel11 = new JPanel();
        jPanel10 = new JPanel();
        jPanel9 = new JPanel();
        panelOptions = new JPanel();
        jPanel1 = new JPanel();
        jPanel3 = new JPanel();
        jPanel4 = new JPanel();
        jButton5 = new JButton();
        jPanel5 = new JPanel();
        jPanel6 = new JPanel();
        jPanel7 = new JPanel();
        panelSession = new JPanel();
        jButton6 = new JButton();
        jButton7 = new JButton();
        jPanel8 = new JPanel();
        jButton8 = new JButton();
        btnCloseEntity = new JButton();
        mainPanelSeprator = new JSeparator();
        mainContent = new JPanel();
        mainNorth = new JPanel();
        jSeparator8 = new JSeparator();
        jPanel2 = new JPanel();
        lblCurrentDate = new JLabel();
        lblLastCompleteDate = new JLabel();
        mainWest = new JPanel();
        mainCenter = new JPanel();
        mainEast = new JPanel();
        jMenuBar1 = new JMenuBar();
        mMerchandise = new JMenu();
        miMeasureUnits = new JMenuItem();
        miTPVCategories = new JMenuItem();
        miCostSheets = new JMenuItem();
        jMenu5 = new JMenu();
        miRawMaterial = new JMenuItem();
        miWorkables = new JMenuItem();
        miProducts = new JMenuItem();
        miExpenses = new JMenuItem();
        mOperations = new JMenu();
        jMenu2 = new JMenu();
        miRawMaterialIncomes = new JMenuItem();
        miExpenseIncomes = new JMenuItem();
        jMenu3 = new JMenu();
        miProductSales = new JMenuItem();
        miRawMaterialOutcomes = new JMenuItem();
        miRawMaterialTransfer = new JMenuItem();
        jSeparator2 = new JPopupMenu.Separator();
        miAjustarProductosNoVendidos = new JMenuItem();
        miExistencias = new JMenuItem();
        mWorkers = new JMenu();
        miRoles = new JMenuItem();
        miAdjustments = new JMenu();
        miBonifications = new JMenuItem();
        miPenalization = new JMenuItem();
        miTaxes = new JMenuItem();
        miRetentions = new JMenuItem();
        jSeparator7 = new JPopupMenu.Separator();
        miStaff = new JMenuItem();
        jSeparator1 = new JPopupMenu.Separator();
        miDocuments = new JMenuItem();
        mActions = new JMenu();
        miMap = new JMenuItem();
        jSeparator6 = new JPopupMenu.Separator();
        miSale = new JMenuItem();
        miInventorySumary = new JMenuItem();
        miInventoryAdjust = new JMenuItem();
        jSeparator3 = new JPopupMenu.Separator();
        miMoveToPreviousDay = new JMenuItem();
        miResetDay = new JMenuItem();
        miMoveToNextDay = new JMenuItem();
        mReports = new JMenu();
        jSeparator9 = new JPopupMenu.Separator();
        jMenuItem4 = new JMenuItem();
        jMenuItem5 = new JMenuItem();
        jMenu4 = new JMenu();
        miPeriodIncomesView = new JMenuItem();
        miPeriodOutcomesView = new JMenuItem();
        jSeparator4 = new JPopupMenu.Separator();
        miPeriodSaleView = new JMenuItem();
        jSeparator5 = new JPopupMenu.Separator();
        miPeriodMovementsView = new JMenuItem();
        mOptions = new JMenu();
        miSettings = new JMenuItem();
        mHelp = new JMenu();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Auditor para " + Setup.enterpriseName + " en " + EntitySelector.currentEntity.getDescription());
        addWindowFocusListener(new WindowFocusListener() {
            public void windowGainedFocus(WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(WindowEvent evt) {
            }
        });

        sideMenu = new JPanel();
        sideMenu.setMinimumSize(new Dimension(300, 475));
        sideMenu.setPreferredSize(new Dimension(299, 500));
        sideMenu.setLayout(new BorderLayout());

        panelMenu.setPreferredSize(new Dimension(300, 50));
        panelMenu.setLayout(new GridLayout(1, 0));

        GroupLayout jPanel12Layout = new GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(jPanel12Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 74, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(jPanel12Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        panelMenu.add(jPanel12);

        GroupLayout jPanel11Layout = new GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(jPanel11Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 74, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(jPanel11Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        panelMenu.add(jPanel11);

        GroupLayout jPanel10Layout = new GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(jPanel10Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 74, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(jPanel10Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        panelMenu.add(jPanel10);

        GroupLayout jPanel9Layout = new GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(jPanel9Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 74, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(jPanel9Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        panelMenu.add(jPanel9);

        sideMenu.add(panelMenu, BorderLayout.NORTH);

        panelOptions.setPreferredSize(new Dimension(300, 400));
        panelOptions.setLayout(new GridLayout(10, 1, 6, 6));

        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 299, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        panelOptions.add(jPanel1);

        GroupLayout jPanel3Layout = new GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 299, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        panelOptions.add(jPanel3);

        GroupLayout jPanel4Layout = new GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 299, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        panelOptions.add(jPanel4);

        jButton5.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        jButton5.setText("Salario Compradores");
        panelOptions.add(jButton5);

        GroupLayout jPanel5Layout = new GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 299, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        panelOptions.add(jPanel5);

        GroupLayout jPanel6Layout = new GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(jPanel6Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 299, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(jPanel6Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        panelOptions.add(jPanel6);

        GroupLayout jPanel7Layout = new GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 299, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        panelOptions.add(jPanel7);

        sideMenu.add(panelOptions, BorderLayout.CENTER);

        panelSession.setPreferredSize(new Dimension(299, 50));
        panelSession.setLayout(new GridLayout(1, 0));

        jButton6.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        jButton6.setText("A");
        panelSession.add(jButton6);

        jButton7.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        jButton7.setText("A");
        panelSession.add(jButton7);

        GroupLayout jPanel8Layout = new GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(jPanel8Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 59, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(jPanel8Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        panelSession.add(jPanel8);

        jButton8.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        jButton8.setText("Generar Cuadre");
        jButton8.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        panelSession.add(jButton8);

        btnCloseEntity.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        btnCloseEntity.setText("Cambiar Entidad");
        btnCloseEntity.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnCloseEntityActionPerformed(evt);
            }
        });
        panelSession.add(btnCloseEntity);

        sideMenu.add(panelSession, BorderLayout.SOUTH);

        getContentPane().add(sideMenu, BorderLayout.WEST);

        mainPanelSeprator.setOrientation(SwingConstants.VERTICAL);
        mainPanelSeprator.setPreferredSize(new Dimension(1, 500));
        getContentPane().add(mainPanelSeprator, BorderLayout.CENTER);

        mainContent.setMinimumSize(new Dimension(700, 475));
        mainContent.setPreferredSize(new Dimension(700, 500));
        mainContent.setLayout(new BorderLayout());

        mainNorth.setPreferredSize(new Dimension(699, 50));
        mainNorth.setLayout(new FlowLayout(FlowLayout.RIGHT));
        mainNorth.add(jSeparator8);

        jPanel2.setLayout(new BoxLayout(jPanel2, BoxLayout.PAGE_AXIS));

        lblCurrentDate.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        lblCurrentDate.setHorizontalAlignment(SwingConstants.RIGHT);
        lblCurrentDate.setText("Fecha Actual: " + LocalDate.now().toString());
        jPanel2.add(lblCurrentDate);

        lblLastCompleteDate.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        lblLastCompleteDate.setHorizontalAlignment(SwingConstants.RIGHT);
        lblLastCompleteDate.setText("Próxima venta: " + EntitySelector.currentEntity.getCurrentDay().toString());
        jPanel2.add(lblLastCompleteDate);

        mainNorth.add(jPanel2);

        mainContent.add(mainNorth, BorderLayout.PAGE_START);

        mainWest.setPreferredSize(new Dimension(0, 450));

        GroupLayout mainWestLayout = new GroupLayout(mainWest);
        mainWest.setLayout(mainWestLayout);
        mainWestLayout.setHorizontalGroup(mainWestLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        mainWestLayout.setVerticalGroup(mainWestLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 901, Short.MAX_VALUE)
        );

        mainContent.add(mainWest, BorderLayout.WEST);

        mainCenter.setToolTipText(" ");
        mainCenter.setPreferredSize(new Dimension(699, 450));
        mainCenter.setLayout(new CardLayout());
        mainContent.add(mainCenter, BorderLayout.CENTER);

        mainEast.setPreferredSize(new Dimension(0, 425));

        GroupLayout mainEastLayout = new GroupLayout(mainEast);
        mainEast.setLayout(mainEastLayout);
        mainEastLayout.setHorizontalGroup(mainEastLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        mainEastLayout.setVerticalGroup(mainEastLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 901, Short.MAX_VALUE)
        );

        mainContent.add(mainEast, BorderLayout.EAST);

        getContentPane().add(mainContent, BorderLayout.EAST);

        mMerchandise.setText("Mercancía");
        mMerchandise.setFont(new Font("Segoe UI", 1, 14)); // NOI18N

        miMeasureUnits.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, InputEvent.ALT_DOWN_MASK));
        miMeasureUnits.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        miMeasureUnits.setText("Unidades de Medida");
        miMeasureUnits.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                miMeasureUnitsActionPerformed(evt);
            }
        });
        mMerchandise.add(miMeasureUnits);

        miTPVCategories.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.ALT_DOWN_MASK));
        miTPVCategories.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        miTPVCategories.setText("Categoria TPV");
        miTPVCategories.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                miTPVCategoriesActionPerformed(evt);
            }
        });
        mMerchandise.add(miTPVCategories);

        miCostSheets.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.ALT_DOWN_MASK));
        miCostSheets.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        miCostSheets.setText("Fichas de Costo");
        miCostSheets.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                miCostSheetsActionPerformed(evt);
            }
        });
        mMerchandise.add(miCostSheets);

        jMenu5.setText("Productos");
        jMenu5.setFont(new Font("Segoe UI", 1, 14)); // NOI18N

        miRawMaterial.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, InputEvent.ALT_DOWN_MASK));
        miRawMaterial.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        miRawMaterial.setText("Materias Primas");
        miRawMaterial.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                miRawMaterialsActionPerformed(evt);
            }
        });
        jMenu5.add(miRawMaterial);

        miWorkables.setVisible(EntitySelector.currentEntity.isWorkable());
        miWorkables.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.ALT_DOWN_MASK));
        miWorkables.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        miWorkables.setText("Recetas");
        miWorkables.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                miWorkablesActionPerformed(evt);
            }
        });
        jMenu5.add(miWorkables);

        miProducts.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.ALT_DOWN_MASK));
        miProducts.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        miProducts.setText("Productos");
        miProducts.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                miProductsActionPerformed(evt);
            }
        });
        jMenu5.add(miProducts);

        mMerchandise.add(jMenu5);

        miExpenses.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.ALT_DOWN_MASK));
        miExpenses.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        miExpenses.setText("Gastos");
        miExpenses.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                miExpensesActionPerformed(evt);
            }
        });
        mMerchandise.add(miExpenses);

        jMenuBar1.add(mMerchandise);

        mOperations.setText("Operaciones");
        mOperations.setFont(new Font("Segoe UI", 1, 14)); // NOI18N

        jMenu2.setText("Entradas");
        jMenu2.setFont(new Font("Segoe UI", 1, 14)); // NOI18N

        miRawMaterialIncomes.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, InputEvent.CTRL_DOWN_MASK));
        miRawMaterialIncomes.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        miRawMaterialIncomes.setText("Materias Primas");
        miRawMaterialIncomes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                miRawMaterialIncomesActionPerformed(evt);
            }
        });
        jMenu2.add(miRawMaterialIncomes);

        miExpenseIncomes.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.CTRL_DOWN_MASK));
        miExpenseIncomes.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        miExpenseIncomes.setText("Gastos");
        miExpenseIncomes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                miExpenseIncomesActionPerformed(evt);
            }
        });
        jMenu2.add(miExpenseIncomes);

        mOperations.add(jMenu2);

        jMenu3.setText("Salidas");
        jMenu3.setFont(new Font("Segoe UI", 1, 14)); // NOI18N

        miProductSales.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_DOWN_MASK));
        miProductSales.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        miProductSales.setText("Productos");
        miProductSales.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                miProductSalesActionPerformed(evt);
            }
        });
        jMenu3.add(miProductSales);

        miRawMaterialOutcomes.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        miRawMaterialOutcomes.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        miRawMaterialOutcomes.setText("Salida de Materias Primas");
        miRawMaterialOutcomes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                miRawMaterialOutcomesActionPerformed(evt);
            }
        });
        jMenu3.add(miRawMaterialOutcomes);

        mOperations.add(jMenu3);

        miRawMaterialTransfer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.CTRL_DOWN_MASK));
        miRawMaterialTransfer.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        miRawMaterialTransfer.setText("Transferencia Interentidad");
        miRawMaterialTransfer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                miRawMaterialTransferActionPerformed(evt);
            }
        });
        mOperations.add(miRawMaterialTransfer);
        mOperations.add(jSeparator2);

        miAjustarProductosNoVendidos.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));
        miAjustarProductosNoVendidos.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        miAjustarProductosNoVendidos.setText("Ajustar productos no vendidos");
        miAjustarProductosNoVendidos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                miAjustarProductosNoVendidosActionPerformed(evt);
            }
        });
        mOperations.add(miAjustarProductosNoVendidos);

        miExistencias.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK));
        miExistencias.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        miExistencias.setText("Existencias");
        miExistencias.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                miExistenciasActionPerformed(evt);
            }
        });
        mOperations.add(miExistencias);

        jMenuBar1.add(mOperations);

        mWorkers.setText("Trabajadores");
        mWorkers.setFont(new Font("Segoe UI", 1, 14)); // NOI18N

        miRoles.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.SHIFT_DOWN_MASK));
        miRoles.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        miRoles.setText("Cargos");
        miRoles.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                miRolesActionPerformed(evt);
            }
        });
        mWorkers.add(miRoles);

        miAdjustments.setText("Ajustes Salariales");
        miAdjustments.setFont(new Font("Segoe UI", 1, 14)); // NOI18N

        miBonifications.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        miBonifications.setText("Bonificaciones");
        miAdjustments.add(miBonifications);

        miPenalization.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        miPenalization.setText("Penalizaciones");
        miAdjustments.add(miPenalization);

        miTaxes.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        miTaxes.setText("Impuestos");
        miAdjustments.add(miTaxes);

        miRetentions.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        miRetentions.setText("Retensiones");
        miAdjustments.add(miRetentions);

        mWorkers.add(miAdjustments);
        mWorkers.add(jSeparator7);

        miStaff.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.SHIFT_DOWN_MASK));
        miStaff.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        miStaff.setText("Plantilla");
        miStaff.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                miStaffActionPerformed(evt);
            }
        });
        mWorkers.add(miStaff);
        mWorkers.add(jSeparator1);

        miDocuments.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.SHIFT_DOWN_MASK));
        miDocuments.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        miDocuments.setText("Documentos ");
        miDocuments.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                miDocumentsActionPerformed(evt);
            }
        });
        mWorkers.add(miDocuments);

        jMenuBar1.add(mWorkers);

        mActions.setText("Inventario");
        mActions.setFont(new Font("Segoe UI", 1, 14)); // NOI18N

        miMap.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, InputEvent.ALT_DOWN_MASK | InputEvent.CTRL_DOWN_MASK));
        miMap.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        miMap.setText("Ajustar Mapeo");
        miMap.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                miMapActionPerformed(evt);
            }
        });
        mActions.add(miMap);
        mActions.add(jSeparator6);

        miSale.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.ALT_DOWN_MASK | InputEvent.CTRL_DOWN_MASK));
        miSale.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        miSale.setText("Registrar Ventas ");
        miSale.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                miSaleActionPerformed(evt);
            }
        });
        mActions.add(miSale);

        miInventorySumary.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.ALT_DOWN_MASK | InputEvent.CTRL_DOWN_MASK));
        miInventorySumary.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        miInventorySumary.setText("Resumen de Ventas");
        miInventorySumary.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                miInventorySumaryActionPerformed(evt);
            }
        });
        mActions.add(miInventorySumary);

        miInventoryAdjust.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.ALT_DOWN_MASK | InputEvent.CTRL_DOWN_MASK));
        miInventoryAdjust.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        miInventoryAdjust.setText("Importar CSV");
        miInventoryAdjust.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                miInventoryAdjustActionPerformed(evt);
            }
        });
        mActions.add(miInventoryAdjust);
        mActions.add(jSeparator3);

        miMoveToPreviousDay.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.ALT_DOWN_MASK | InputEvent.CTRL_DOWN_MASK));
        miMoveToPreviousDay.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        miMoveToPreviousDay.setText("Volver a la Jornada Anterior");
        miMoveToPreviousDay.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                miMoveToPreviousDayActionPerformed(evt);
            }
        });
        mActions.add(miMoveToPreviousDay);

        miResetDay.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.ALT_DOWN_MASK | InputEvent.CTRL_DOWN_MASK));
        miResetDay.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        miResetDay.setText("Reiniciar Jornada");
        miResetDay.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                miResetDayActionPerformed(evt);
            }
        });
        mActions.add(miResetDay);

        miMoveToNextDay.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.ALT_DOWN_MASK | InputEvent.CTRL_DOWN_MASK));
        miMoveToNextDay.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        miMoveToNextDay.setText("Terminar Jornada Actual");
        miMoveToNextDay.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                miMoveToNextDayActionPerformed(evt);
            }
        });
        mActions.add(miMoveToNextDay);

        jMenuBar1.add(mActions);

        mReports.setText("Informes");
        mReports.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        mReports.add(jSeparator9);

        jMenuItem4.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.SHIFT_DOWN_MASK | InputEvent.CTRL_DOWN_MASK));
        jMenuItem4.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        jMenuItem4.setText("Materias Primas");
        jMenuItem4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        mReports.add(jMenuItem4);

        jMenuItem5.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.SHIFT_DOWN_MASK | InputEvent.CTRL_DOWN_MASK));
        jMenuItem5.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        jMenuItem5.setText("Productos a la Venta");
        jMenuItem5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        mReports.add(jMenuItem5);

        jMenu4.setText("Inventario");
        jMenu4.setFont(new Font("Segoe UI", 1, 14)); // NOI18N

        miPeriodIncomesView.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.SHIFT_DOWN_MASK | InputEvent.CTRL_DOWN_MASK));
        miPeriodIncomesView.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        miPeriodIncomesView.setText("Entradas del Periodo");
        miPeriodIncomesView.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                miPeriodIncomesViewActionPerformed(evt);
            }
        });
        jMenu4.add(miPeriodIncomesView);

        miPeriodOutcomesView.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.SHIFT_DOWN_MASK | InputEvent.CTRL_DOWN_MASK));
        miPeriodOutcomesView.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        miPeriodOutcomesView.setText("Salidas del Periodo");
        miPeriodOutcomesView.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                miPeriodOutcomesViewActionPerformed(evt);
            }
        });
        jMenu4.add(miPeriodOutcomesView);
        jMenu4.add(jSeparator4);

        miPeriodSaleView.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.SHIFT_DOWN_MASK | InputEvent.CTRL_DOWN_MASK));
        miPeriodSaleView.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        miPeriodSaleView.setText("Venta del Periodo");
        miPeriodSaleView.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                miPeriodSaleViewActionPerformed(evt);
            }
        });
        jMenu4.add(miPeriodSaleView);
        jMenu4.add(jSeparator5);

        miPeriodMovementsView.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, InputEvent.SHIFT_DOWN_MASK | InputEvent.CTRL_DOWN_MASK));
        miPeriodMovementsView.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        miPeriodMovementsView.setText("Movimientos del Periodo");
        miPeriodMovementsView.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                miPeriodMovementsViewActionPerformed(evt);
            }
        });
        jMenu4.add(miPeriodMovementsView);

        mReports.add(jMenu4);

        jMenuBar1.add(mReports);

        mOptions.setText("Opciones");
        mOptions.setFont(new Font("Segoe UI", 1, 14)); // NOI18N

        miSettings.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.ALT_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK | InputEvent.CTRL_DOWN_MASK));
        miSettings.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        miSettings.setText("Configuración");
        miSettings.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                miSettingsActionPerformed(evt);
            }
        });
        mOptions.add(miSettings);

        jMenuBar1.add(mOptions);

        mHelp.setText("Ayuda");
        mHelp.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        jMenuBar1.add(mHelp);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void miMeasureUnitsActionPerformed(ActionEvent evt) {//GEN-FIRST:event_miMeasureUnitsActionPerformed
        MeasureUnitsGestion dialog = new MeasureUnitsGestion(this, false, connection);
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(true);
        dialog.setVisible(true);
    }//GEN-LAST:event_miMeasureUnitsActionPerformed

    private void miRawMaterialIncomesActionPerformed(ActionEvent evt) {//GEN-FIRST:event_miRawMaterialIncomesActionPerformed
        IncomesOperGestion dialog = new IncomesOperGestion(this, false, operationsCRUD, itemsCRUD, true);
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(true);
        dialog.setVisible(true);
    }//GEN-LAST:event_miRawMaterialIncomesActionPerformed

    private void miProductsActionPerformed(ActionEvent evt) {//GEN-FIRST:event_miProductsActionPerformed
        try {
            ProductsGestion dialog = new ProductsGestion(this, false, itemsCRUD);
            dialog.setLocationRelativeTo(this);
            dialog.setResizable(true);
            dialog.setVisible(true);
        } catch (SQLException ex) {
            Logger.getInstance().updateErrorLog(ex);
        }
    }//GEN-LAST:event_miProductsActionPerformed

    private void miExistenciasActionPerformed(ActionEvent evt) {//GEN-FIRST:event_miExistenciasActionPerformed
        Inventory dialog = new Inventory(this, operationsCRUD, false);
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(true);
        dialog.setVisible(true);
    }//GEN-LAST:event_miExistenciasActionPerformed

    private void miProductSalesActionPerformed(ActionEvent evt) {//GEN-FIRST:event_miProductSalesActionPerformed
        SaleOperGestion dialog = new SaleOperGestion(this, false, operationsCRUD, itemsCRUD, false);
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(true);
        dialog.setVisible(true);
    }//GEN-LAST:event_miProductSalesActionPerformed

    private void miRawMaterialsActionPerformed(ActionEvent evt) {//GEN-FIRST:event_miRawMaterialsActionPerformed
        RawMaterialsGestion dialog = new RawMaterialsGestion(this, false, operationsCRUD, itemsCRUD);
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(true);
        dialog.setVisible(true);
    }//GEN-LAST:event_miRawMaterialsActionPerformed

    private void miInventorySumaryActionPerformed(ActionEvent evt) {//GEN-FIRST:event_miInventorySumaryActionPerformed
        SaleFromDate dialog = new SaleFromDate(this, false, operationsCRUD, itemsCRUD, daysController);
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(true);
        dialog.setVisible(true);
    }//GEN-LAST:event_miInventorySumaryActionPerformed

    private void miWorkablesActionPerformed(ActionEvent evt) {//GEN-FIRST:event_miWorkablesActionPerformed
        WorkablesGestion dialog = new WorkablesGestion(this, false, itemsCRUD);
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(true);
        dialog.setVisible(true);
    }//GEN-LAST:event_miWorkablesActionPerformed

    private void miSaleActionPerformed(ActionEvent evt) {//GEN-FIRST:event_miSaleActionPerformed
        try {
            MakeSale dialog = new MakeSale(this, false, connection, operationsCRUD, itemsCRUD, daysController);
            dialog.setLocationRelativeTo(this);
            dialog.setResizable(true);
            dialog.setVisible(true);
        } catch (SQLException ex) {
            Logger.getInstance().updateErrorLog(ex);
        }
    }//GEN-LAST:event_miSaleActionPerformed

    private void miInventoryAdjustActionPerformed(ActionEvent evt) {//GEN-FIRST:event_miInventoryAdjustActionPerformed
        LoadCSV dialog = new LoadCSV(this, false, operationsCRUD, itemsCRUD, daysController);
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(true);
        dialog.setVisible(true);
    }//GEN-LAST:event_miInventoryAdjustActionPerformed

    private void miPeriodIncomesViewActionPerformed(ActionEvent evt) {//GEN-FIRST:event_miPeriodIncomesViewActionPerformed
        R_PeriodIncomes dialog = new R_PeriodIncomes(this, false, connection, operationsCRUD, itemsCRUD);
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(true);
        dialog.setVisible(true);
    }//GEN-LAST:event_miPeriodIncomesViewActionPerformed

    private void miPeriodOutcomesViewActionPerformed(ActionEvent evt) {//GEN-FIRST:event_miPeriodOutcomesViewActionPerformed
        R_PeriodOutcomes dialog = new R_PeriodOutcomes(this, false, connection, operationsCRUD, itemsCRUD);
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(true);
        dialog.setVisible(true);
    }//GEN-LAST:event_miPeriodOutcomesViewActionPerformed

    private void miPeriodMovementsViewActionPerformed(ActionEvent evt) {//GEN-FIRST:event_miPeriodMovementsViewActionPerformed
        R_PeriodMovements dialog = new R_PeriodMovements(this, false, connection, operationsCRUD, itemsCRUD);
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(true);
        dialog.setVisible(true);
    }//GEN-LAST:event_miPeriodMovementsViewActionPerformed

    private void miPeriodSaleViewActionPerformed(ActionEvent evt) {//GEN-FIRST:event_miPeriodSaleViewActionPerformed
        R_PeriodSales dialog = new R_PeriodSales(this, false, connection, operationsCRUD, itemsCRUD);
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(true);
        dialog.setVisible(true);
    }//GEN-LAST:event_miPeriodSaleViewActionPerformed

    private void formWindowGainedFocus(WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        refreshCruds();
    }//GEN-LAST:event_formWindowGainedFocus

    private void miMoveToNextDayActionPerformed(ActionEvent evt) {//GEN-FIRST:event_miMoveToNextDayActionPerformed
        try {
            int opt = JOptionPane.showConfirmDialog(this, "¿Está seguro que desea completar esta jornada?", "Confirmación", JOptionPane.YES_NO_OPTION);
            if (opt == 0) {
                daysController.closeDay();
                refreshCruds();
                JOptionPane.showMessageDialog(this, "Jornada Completada", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            Logger.getInstance().updateErrorLog(ex);
        }
    }//GEN-LAST:event_miMoveToNextDayActionPerformed

    private void miResetDayActionPerformed(ActionEvent evt) {//GEN-FIRST:event_miResetDayActionPerformed
        try {
            int opt = JOptionPane.showConfirmDialog(this, "¿Está seguro que desea reinciar esta jornada?\nTenga en cuenta que esto borrará todas las operaciones de esta fecha.", "Confirmación", JOptionPane.YES_NO_OPTION);
            if (opt == 0) {
                daysController.unfillDay();
                refreshCruds();
                JOptionPane.showMessageDialog(this, "Jornada Reiniciada", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            Logger.getInstance().updateErrorLog(ex);
        }
    }//GEN-LAST:event_miResetDayActionPerformed

    private void miCostSheetsActionPerformed(ActionEvent evt) {//GEN-FIRST:event_miCostSheetsActionPerformed
        CostSheetsGestion dialog = new CostSheetsGestion(this, false, connection);
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(true);
        dialog.setVisible(true);
    }//GEN-LAST:event_miCostSheetsActionPerformed

    private void miMoveToPreviousDayActionPerformed(ActionEvent evt) {//GEN-FIRST:event_miMoveToPreviousDayActionPerformed
        try {
            int opt = JOptionPane.showConfirmDialog(this, "¿Está seguro que desea retroceder a la jornada anterior?\nTenga en cuenta que esto borrará todas las operaciones de este dia y el anterior.", "Confirmación", JOptionPane.YES_NO_OPTION);
            if (opt == 0) {
                daysController.rewindDay();
                refreshCruds();
                JOptionPane.showMessageDialog(this, "Jornada retocedida", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            Logger.getInstance().updateErrorLog(ex);
        }
    }//GEN-LAST:event_miMoveToPreviousDayActionPerformed

    private void miExpensesActionPerformed(ActionEvent evt) {//GEN-FIRST:event_miExpensesActionPerformed
        ExpensesGestion dialog = new ExpensesGestion(this, itemsCRUD, false);
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(true);
        dialog.setVisible(true);
    }//GEN-LAST:event_miExpensesActionPerformed

    private void miMapActionPerformed(ActionEvent evt) {//GEN-FIRST:event_miMapActionPerformed
        try {
            MapperGestion dialogMakeCurrentsDaySaleMap = new MapperGestion(this, false, connection, null, operationsCRUD, itemsCRUD, null);
            dialogMakeCurrentsDaySaleMap.setLocationRelativeTo(null);
            dialogMakeCurrentsDaySaleMap.setVisible(true);
        } catch (SQLException ex) {
            Logger.getInstance().updateErrorLog(ex);
        }
    }//GEN-LAST:event_miMapActionPerformed

    private void miRawMaterialOutcomesActionPerformed(ActionEvent evt) {//GEN-FIRST:event_miRawMaterialOutcomesActionPerformed
        OutcomesOperGestion dialog = new OutcomesOperGestion(this, false, operationsCRUD, itemsCRUD, false);
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(true);
        dialog.setVisible(true);
    }//GEN-LAST:event_miRawMaterialOutcomesActionPerformed

    private void miExpenseIncomesActionPerformed(ActionEvent evt) {//GEN-FIRST:event_miExpenseIncomesActionPerformed
        ExpensesOperGestion dialog = new ExpensesOperGestion(this, false, operationsCRUD, itemsCRUD, true);
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(true);
        dialog.setVisible(true);
    }//GEN-LAST:event_miExpenseIncomesActionPerformed

    private void miRolesActionPerformed(ActionEvent evt) {//GEN-FIRST:event_miRolesActionPerformed
        RolesGestion dialog = new RolesGestion(this, false, connection);
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(true);
        dialog.setVisible(true);
    }//GEN-LAST:event_miRolesActionPerformed

    private void jMenuItem4ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        DialogReportsRawMaterials dialog = new DialogReportsRawMaterials(this, false, connection, operationsCRUD, itemsCRUD);
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(true);
        dialog.setVisible(true);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem5ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        DialogReportsProducts dialog = new DialogReportsProducts(this, false, connection, operationsCRUD, itemsCRUD);
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(true);
        dialog.setVisible(true);
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void miStaffActionPerformed(ActionEvent evt) {//GEN-FIRST:event_miStaffActionPerformed
        StaffGestion dialog = new StaffGestion(this, workersCRUD, false);
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(true);
        dialog.setVisible(true);
    }//GEN-LAST:event_miStaffActionPerformed

    private void btnCloseEntityActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnCloseEntityActionPerformed
        try {
            EntitySelector dialog = new EntitySelector(postgreSQL, entitiesCRUD);
            dialog.setLocationRelativeTo(this);
            dialog.setResizable(true);
            dialog.setVisible(true);
            dispose();
        } catch (SQLException ex) {
            Logger.getInstance().updateErrorLog(ex);
        }
    }//GEN-LAST:event_btnCloseEntityActionPerformed

    private void miSettingsActionPerformed(ActionEvent evt) {//GEN-FIRST:event_miSettingsActionPerformed
        SettingsDialog dialog = new SettingsDialog(this, false, connection, itemsCRUD, customComparator);//ddd
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(true);
        dialog.setVisible(true);
    }//GEN-LAST:event_miSettingsActionPerformed

    private void miDocumentsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miDocumentsActionPerformed
        try {
            DocumentsGestion dialog = new DocumentsGestion(this, false, connection, workersCRUD);
            dialog.setLocationRelativeTo(this);
            dialog.setResizable(true);
            dialog.setVisible(true);
        } catch (SQLException ex) {
            Logger.getInstance().updateErrorLog(ex);
        }
    }//GEN-LAST:event_miDocumentsActionPerformed

    private void miRawMaterialTransferActionPerformed(ActionEvent evt) {//GEN-FIRST:event_miRawMaterialTransferActionPerformed
        TransferOperGestion dialog = new TransferOperGestion(this, false, entitiesCRUD, operationsCRUD);
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(true);
        dialog.setVisible(true);
    }//GEN-LAST:event_miRawMaterialTransferActionPerformed

    private void jButton8ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        try {
            CreateCuadreFile.execute(connection, itemsCRUD);
        } catch (SQLException | IOException ex) {
            Logger.getInstance().updateErrorLog(ex);
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void miTPVCategoriesActionPerformed(ActionEvent evt) {//GEN-FIRST:event_miTPVCategoriesActionPerformed
        TPVCategoriesGestion dialog = new TPVCategoriesGestion(this, false, connection);
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(true);
        dialog.setVisible(true);
    }//GEN-LAST:event_miTPVCategoriesActionPerformed

    private void miAjustarProductosNoVendidosActionPerformed(ActionEvent evt) {//GEN-FIRST:event_miAjustarProductosNoVendidosActionPerformed
        AdjustNotSoldProducts dialog = new AdjustNotSoldProducts(this, false, operationsCRUD, itemsCRUD);
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(true);
        dialog.setVisible(true);
    }//GEN-LAST:event_miAjustarProductosNoVendidosActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton btnCloseEntity;
    private JButton jButton5;
    private JButton jButton6;
    private JButton jButton7;
    private JButton jButton8;
    private JMenu jMenu2;
    private JMenu jMenu3;
    private JMenu jMenu4;
    private JMenu jMenu5;
    private JMenuBar jMenuBar1;
    private JMenuItem jMenuItem4;
    private JMenuItem jMenuItem5;
    private JPanel jPanel1;
    private JPanel jPanel10;
    private JPanel jPanel11;
    private JPanel jPanel12;
    private JPanel jPanel2;
    private JPanel jPanel3;
    private JPanel jPanel4;
    private JPanel jPanel5;
    private JPanel jPanel6;
    private JPanel jPanel7;
    private JPanel jPanel8;
    private JPanel jPanel9;
    private JPopupMenu.Separator jSeparator1;
    private JPopupMenu.Separator jSeparator2;
    private JPopupMenu.Separator jSeparator3;
    private JPopupMenu.Separator jSeparator4;
    private JPopupMenu.Separator jSeparator5;
    private JPopupMenu.Separator jSeparator6;
    private JPopupMenu.Separator jSeparator7;
    private JSeparator jSeparator8;
    private JPopupMenu.Separator jSeparator9;
    private JLabel lblCurrentDate;
    private JLabel lblLastCompleteDate;
    private JMenu mActions;
    private JMenu mHelp;
    private JMenu mMerchandise;
    private JMenu mOperations;
    private JMenu mOptions;
    private JMenu mReports;
    private JMenu mWorkers;
    private JPanel mainCenter;
    private JPanel mainContent;
    private JPanel mainEast;
    private JPanel mainNorth;
    private JSeparator mainPanelSeprator;
    private JPanel mainWest;
    private JMenu miAdjustments;
    private JMenuItem miAjustarProductosNoVendidos;
    private JMenuItem miBonifications;
    private JMenuItem miCostSheets;
    private JMenuItem miDocuments;
    private JMenuItem miExistencias;
    private JMenuItem miExpenseIncomes;
    private JMenuItem miExpenses;
    private JMenuItem miInventoryAdjust;
    private JMenuItem miInventorySumary;
    private JMenuItem miMap;
    private JMenuItem miMeasureUnits;
    private JMenuItem miMoveToNextDay;
    private JMenuItem miMoveToPreviousDay;
    private JMenuItem miPenalization;
    private JMenuItem miPeriodIncomesView;
    private JMenuItem miPeriodMovementsView;
    private JMenuItem miPeriodOutcomesView;
    private JMenuItem miPeriodSaleView;
    private JMenuItem miProductSales;
    private JMenuItem miProducts;
    private JMenuItem miRawMaterial;
    private JMenuItem miRawMaterialIncomes;
    private JMenuItem miRawMaterialOutcomes;
    private JMenuItem miRawMaterialTransfer;
    private JMenuItem miResetDay;
    private JMenuItem miRetentions;
    private JMenuItem miRoles;
    private JMenuItem miSale;
    private JMenuItem miSettings;
    private JMenuItem miStaff;
    private JMenuItem miTPVCategories;
    private JMenuItem miTaxes;
    private JMenuItem miWorkables;
    private JPanel panelMenu;
    private JPanel panelOptions;
    private JPanel panelSession;
    private JPanel sideMenu;
    // End of variables declaration//GEN-END:variables

    private void setupQuickActions() {
        modernContentPanel.addQuickAction("Generar Cuadre", () -> {
            try {
                CreateCuadreFile.execute(connection, itemsCRUD);
            } catch (SQLException | IOException ex) {
                Logger.getInstance().updateErrorLog(ex);
            }
        });

        modernContentPanel.addQuickAction("Ver Mapa", () -> {
            try {
                MapperGestion dialog = new MapperGestion(this, false, connection, null, operationsCRUD, itemsCRUD, null);
                showDialog(dialog);
            } catch (SQLException ex) {
                Logger.getInstance().updateErrorLog(ex);
            }
        });
    }

    private void showDialog(JDialog dialog) {
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(true);
        dialog.setVisible(true);
    }

    private void refreshCruds() {
        try {
            entitiesCRUD.reloadDB();
            EntitySelector.currentEntity = entitiesCRUD.get(EntitySelector.currentEntity.getId());
            operationsCRUD.reloadDB();
            itemsCRUD.reloadDB();
            workersCRUD.reloadDB();
            setTitle("Auditor para " + Setup.enterpriseName + " en " + EntitySelector.currentEntity.getDescription());
            lblLastCompleteDate.setText("Próxima venta: " + EntitySelector.currentEntity.getCurrentDay().toString());
        } catch (SQLException ex) {
            Logger.getInstance().updateErrorLog(ex);
        }
    }

    private void saveProperties(Properties options) {
        options.put("frameHeight", String.valueOf(getHeight()));
        options.put("frameWidth", String.valueOf(getWidth()));
        options.put("extendedState", String.valueOf(getExtendedState()));
        options.put("comparator", customComparator.getSelectedColumn().toString());
        Settings.updateOptions(options);
    }

}
