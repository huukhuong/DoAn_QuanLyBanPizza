package GUI;

import BUS.LoaiBUS;
import BUS.NhanVienBUS;
import BUS.SanPhamBUS;
import DTO.LoaiSP;
import DTO.NhanVien;
import DTO.SanPham;
import static Main.Main.changLNF;
import MyCustom.TransparentPanel;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.*;
import java.util.*;
import javax.imageio.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.text.*;

public class BanHangGUI extends JPanel {

    private SanPhamBUS spBUS = new SanPhamBUS();
    private NhanVienBUS nvBUS = new NhanVienBUS();
    private LoaiBUS loaiBUS = new LoaiBUS();

    JLabel lblTabbedBanHang, lblTabbedHoaDon;
    final ImageIcon tabbedSelected = new ImageIcon("image/ManagerUI/tabbed-btn--selected.png");
    final ImageIcon tabbedDefault = new ImageIcon("image/ManagerUI/tabbed-btn.png");
    final Color colorPanel = new Color(247, 247, 247);
    CardLayout cardBanHangGroup = new CardLayout();
    JPanel pnCardTabBanHang;
    JTable tblBanHang, tblGioHang;
    DefaultTableModel dtmSanPhamBan, dtmGioHang;
    JTextField txtMaSPBanHang, txtTenSPBanHang, txtDonGiaBanHang;
    JSpinner spnSoLuongBanHang;
    JComboBox<String> cmbLoaiSPBanHang, cmbNhanVienBan;
    JLabel btnThemVaoGio, lblAnhSP, btnXoaSPGioHang, btnXuatHoaDonSP;

    public BanHangGUI() {
        changLNF("Windows");
        addControlsBanHang();
        addEventsBanHang();
    }

    private void addControlsBanHang() {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        this.setLayout(new BorderLayout());
        this.setBackground(colorPanel);

        int w = 1030;
        int h = 844;

        /*
        =========================================================================
                                    PANEL TABBED
        =========================================================================
         */
        JPanel pnTop = new TransparentPanel();
        //<editor-fold defaultstate="collapsed" desc="Panel Tab Bán hàng & Hoá đơn">
        Font font = new Font("", Font.PLAIN, 20);
        pnTop.setPreferredSize(new Dimension(w, 41));
        pnTop.setLayout(null);
        pnTop.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.GRAY));

        lblTabbedBanHang = new JLabel("Bán hàng");
        lblTabbedBanHang.setHorizontalTextPosition(JLabel.CENTER);
        lblTabbedBanHang.setVerticalTextPosition(JLabel.CENTER);
        lblTabbedBanHang.setIcon(tabbedSelected);
        lblTabbedBanHang.setBounds(2, 2, 140, 37);
        lblTabbedBanHang.setFont(font);
        lblTabbedBanHang.setForeground(Color.white);
        lblTabbedBanHang.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        lblTabbedHoaDon = new JLabel("Hoá đơn");
        lblTabbedHoaDon.setHorizontalTextPosition(JLabel.CENTER);
        lblTabbedHoaDon.setVerticalTextPosition(JLabel.CENTER);
        lblTabbedHoaDon.setIcon(tabbedDefault);
        lblTabbedHoaDon.setBounds(143, 2, 140, 37);
        lblTabbedHoaDon.setFont(font);
        lblTabbedHoaDon.setForeground(Color.white);
        lblTabbedHoaDon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        pnTop.add(lblTabbedBanHang);
        pnTop.add(lblTabbedHoaDon);
        //</editor-fold>
        this.add(pnTop, BorderLayout.NORTH);
        /*
        =========================================================================
                                    PANEL CT BÁN HÀNG
        =========================================================================
         */

        JPanel pnCTBanHang = new TransparentPanel();
        pnCTBanHang.setLayout(new BoxLayout(pnCTBanHang, BoxLayout.Y_AXIS));

        //====================Bảng hàng hoá====================
        //<editor-fold defaultstate="collapsed" desc="Bảng sản phẩm">
        JPanel pnTableBanHang = new TransparentPanel();
        pnTableBanHang.setLayout(new BorderLayout());

        JLabel lblTitleBanHang = new JLabel("Danh sách sản phẩm");
        lblTitleBanHang.setFont(new Font("Arial", Font.BOLD, 28));
        pnTableBanHang.add(lblTitleBanHang, BorderLayout.NORTH);

        dtmSanPhamBan = new DefaultTableModel();
        dtmSanPhamBan.addColumn("Mã SP");
        dtmSanPhamBan.addColumn("Tên SP");
        dtmSanPhamBan.addColumn("Đơn giá");
        dtmSanPhamBan.addColumn("Còn lại");
        dtmSanPhamBan.addColumn("Đơn vị tính");
        dtmSanPhamBan.addColumn("Ảnh");
        tblBanHang = new JTable(dtmSanPhamBan);

        customTable(tblBanHang);

        tblBanHang.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tblBanHang.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        tblBanHang.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        tblBanHang.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);

        TableColumnModel columnModelBanHang = tblBanHang.getColumnModel();
        columnModelBanHang.getColumn(0).setPreferredWidth(77);
        columnModelBanHang.getColumn(1).setPreferredWidth(282);
        columnModelBanHang.getColumn(2).setPreferredWidth(82);
        columnModelBanHang.getColumn(3).setPreferredWidth(85);
        columnModelBanHang.getColumn(4).setPreferredWidth(138);
        columnModelBanHang.getColumn(5).setPreferredWidth(0);

        JScrollPane scrTblBanHang = new JScrollPane(tblBanHang);
        //</editor-fold>
        pnTableBanHang.add(scrTblBanHang, BorderLayout.CENTER);

        //====================Thông tin giỏ hàng====================
        JPanel pnTableGioHang = new TransparentPanel();
        //<editor-fold defaultstate="collapsed" desc="Bảng giỏ hàng">
        pnTableGioHang.setLayout(new BorderLayout());

        JLabel lblTitleGioHang = new JLabel("Giỏ hàng");
        lblTitleGioHang.setFont(new Font("Arial", Font.BOLD, 28));
        pnTableGioHang.add(lblTitleGioHang, BorderLayout.NORTH);

        dtmGioHang = new DefaultTableModel();
        dtmGioHang.addColumn("Mã SP");
        dtmGioHang.addColumn("Tên SP");
        dtmGioHang.addColumn("Số lượng");
        dtmGioHang.addColumn("Đơn giá");
        dtmGioHang.addColumn("Thành tiền");

        tblGioHang = new JTable(dtmGioHang);

        customTable(tblGioHang);

        tblGioHang.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tblGioHang.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        tblGioHang.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        tblGioHang.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);

        TableColumnModel columnModelGioHang = tblGioHang.getColumnModel();
        columnModelGioHang.getColumn(0).setPreferredWidth(81);
        columnModelGioHang.getColumn(1).setPreferredWidth(279);
        columnModelGioHang.getColumn(2).setPreferredWidth(111);
        columnModelGioHang.getColumn(3).setPreferredWidth(101);
        columnModelGioHang.getColumn(4).setPreferredWidth(100);

        JScrollPane scrTblGioHang = new JScrollPane(tblGioHang);
        //</editor-fold>
        pnTableGioHang.add(scrTblGioHang, BorderLayout.CENTER);

        //====================Thông tin bán hàng====================
        JPanel pnThongTinBanHang = new TransparentPanel();
        //<editor-fold defaultstate="collapsed" desc="Thông tin bán hàng (textfield, button thêm)">

        pnThongTinBanHang.setLayout(new BoxLayout(pnThongTinBanHang, BoxLayout.Y_AXIS));

        JPanel pnTitleThongTin = new TransparentPanel();
        JLabel lblTitleThongTin = new JLabel("Chi tiết sản phẩm", JLabel.LEFT);
        lblTitleThongTin.setFont(new Font("Arial", Font.BOLD, 28));
        pnTitleThongTin.add(lblTitleThongTin);
        pnThongTinBanHang.add(pnTitleThongTin);

        JPanel pnLoaiSP = new TransparentPanel();
        JLabel lblLoai = new JLabel("Loại SP");
        lblLoai.setFont(font);
        cmbLoaiSPBanHang = new JComboBox<>();
        cmbLoaiSPBanHang.setFont(font);
        loadDataComboboxLoaiBanSP();
        pnLoaiSP.add(lblLoai);
        pnLoaiSP.add(cmbLoaiSPBanHang);
        pnThongTinBanHang.add(pnLoaiSP);

        JPanel pnMaSP = new TransparentPanel();
        JLabel lblMa = new JLabel("Mã SP");
        lblMa.setFont(font);
        txtMaSPBanHang = new JTextField(15);
        txtMaSPBanHang.setFont(font);
        pnMaSP.add(lblMa);
        pnMaSP.add(txtMaSPBanHang);
        pnThongTinBanHang.add(pnMaSP);

        JPanel pnTenSP = new TransparentPanel();
        JLabel lblTen = new JLabel("Tên SP");
        lblTen.setFont(font);
        txtTenSPBanHang = new JTextField(15);
        txtTenSPBanHang.setFont(font);
        pnTenSP.add(lblTen);
        pnTenSP.add(txtTenSPBanHang);
        pnThongTinBanHang.add(pnTenSP);

        JPanel pnDonGiaSP = new TransparentPanel();
        JLabel lblDonGia = new JLabel("Đơn giá");
        lblDonGia.setFont(font);
        txtDonGiaBanHang = new JTextField(15);
        txtDonGiaBanHang.setFont(font);
        pnDonGiaSP.add(lblDonGia);
        pnDonGiaSP.add(txtDonGiaBanHang);
        pnThongTinBanHang.add(pnDonGiaSP);

        JPanel pnSoLuongSP = new TransparentPanel();
        JLabel lblSoLuong = new JLabel("Số lượng");
        lblSoLuong.setFont(font);
        spnSoLuongBanHang = new JSpinner();
        spnSoLuongBanHang.setFont(font);
        SpinnerNumberModel modeSpinner = new SpinnerNumberModel(1, 1, 100, 1);
        spnSoLuongBanHang.setModel(modeSpinner);
        JFormattedTextField txtSpinner = ((JSpinner.NumberEditor) spnSoLuongBanHang.getEditor()).getTextField();
        ((NumberFormatter) txtSpinner.getFormatter()).setAllowsInvalid(false);
        txtSpinner.setEditable(false);
        txtSpinner.setHorizontalAlignment(JTextField.LEFT);

        pnSoLuongSP.add(lblSoLuong);
        pnSoLuongSP.add(spnSoLuongBanHang);
        pnThongTinBanHang.add(pnSoLuongSP);

        JPanel pnNhanVienBan = new TransparentPanel();
        JLabel lblNhanVien = new JLabel("Nhân Viên");
        lblNhanVien.setFont(font);
        lblLoai.setFont(font);
        cmbNhanVienBan = new JComboBox<>();
        cmbNhanVienBan.setFont(font);
        loadDataComboboxNhanVienBan();
        pnNhanVienBan.add(lblNhanVien);
        pnNhanVienBan.add(cmbNhanVienBan);
        pnThongTinBanHang.add(pnNhanVienBan);

        JPanel pnButtonBan = new TransparentPanel();
        btnThemVaoGio = new JLabel("Thêm vào giỏ");
        pnButtonBan.add(btnThemVaoGio);
        pnThongTinBanHang.add(pnButtonBan);

        cmbLoaiSPBanHang.setPreferredSize(txtMaSPBanHang.getPreferredSize());
        Dimension sizeLabel = lblNhanVien.getPreferredSize();
        lblLoai.setPreferredSize(sizeLabel);
        lblMa.setPreferredSize(sizeLabel);
        lblTen.setPreferredSize(sizeLabel);
        lblDonGia.setPreferredSize(sizeLabel);
        lblSoLuong.setPreferredSize(sizeLabel);
        spnSoLuongBanHang.setPreferredSize(txtMaSPBanHang.getPreferredSize());
        cmbNhanVienBan.setPreferredSize(txtMaSPBanHang.getPreferredSize());

        txtMaSPBanHang.setEditable(false);
        txtDonGiaBanHang.setEditable(false);
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Ảnh hàng">
        JPanel pnAnhSanPham = new TransparentPanel();
        pnAnhSanPham.setPreferredSize(new Dimension((int) pnThongTinBanHang.getPreferredSize().getWidth(), 220));
        lblAnhSP = new JLabel();
        lblAnhSP.setBorder(BorderFactory.createLineBorder(Color.gray));
        pnAnhSanPham.add(lblAnhSP);

        JPanel pnButtonBanHang = new TransparentPanel();
        btnXoaSPGioHang = new JLabel("Xoá");
        btnXuatHoaDonSP = new JLabel("Xuất hoá đơn");
        pnButtonBanHang.setPreferredSize(new Dimension((int) pnThongTinBanHang.getPreferredSize().getWidth(), 50));

        //<editor-fold defaultstate="collapsed" desc="Action cho button">
        ArrayList<JLabel> btnSPList = new ArrayList();
        btnSPList.add(btnThemVaoGio);
        btnSPList.add(btnXoaSPGioHang);
        btnSPList.add(btnXuatHoaDonSP);
        for (JLabel lbl : btnSPList) {
            lbl.setFont(font);
            lbl.setForeground(Color.white);
            lbl.setIcon(new ImageIcon("image/ManagerUI/btn-BanHang.png"));
            lbl.setHorizontalTextPosition(JLabel.CENTER);
            lbl.setVerticalTextPosition(JLabel.CENTER);
            lbl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            if (lbl != btnThemVaoGio) {
                JPanel pnTemp = new TransparentPanel();
                pnTemp.add(lbl);
                pnButtonBanHang.add(pnTemp);
            }
            lbl.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                }

                @Override
                public void mousePressed(MouseEvent e) {
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    lbl.setIcon(new ImageIcon("image/ManagerUI/btn-BanHang--hover.png"));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    lbl.setIcon(new ImageIcon("image/ManagerUI/btn-BanHang.png"));
                }
            });
        }
        //</editor-fold>

        //</editor-fold>

        /*
        =========================================================================
                                    PANEL CT HOÁ ĐƠN
        =========================================================================
         */
        JPanel pnCTHoaDon = new JPanel();

        pnCardTabBanHang = new JPanel(cardBanHangGroup);
        pnCardTabBanHang.add(pnCTBanHang, "1");
        pnCardTabBanHang.add(pnCTHoaDon, "2");
        pnCardTabBanHang.setPreferredSize(new Dimension(w, (int) (h - pnTop.getPreferredSize().getHeight())));

        //=======================================================
        //=======================================================
        JPanel pnCenter = new TransparentPanel();

        JPanel pnLeftBanHang = new TransparentPanel();
        pnLeftBanHang.setLayout(new BoxLayout(pnLeftBanHang, BoxLayout.Y_AXIS));
        pnLeftBanHang.setPreferredSize(new Dimension(618, h - 41));
        pnTableBanHang.setPreferredSize(new Dimension(618, (h - 41) / 2));
        pnLeftBanHang.add(pnTableBanHang);
        pnLeftBanHang.add(pnTableGioHang);
        pnCenter.add(pnLeftBanHang);

        JPanel pnRightBanHang = new TransparentPanel();
        pnRightBanHang.setLayout(new BoxLayout(pnRightBanHang, BoxLayout.Y_AXIS));

        pnRightBanHang.add(pnThongTinBanHang);
        pnThongTinBanHang.setPreferredSize(new Dimension((int) pnRightBanHang.getPreferredSize().getWidth(),
                (int) pnTableBanHang.getPreferredSize().getHeight()));

        pnRightBanHang.add(pnAnhSanPham);
        pnRightBanHang.add(pnButtonBanHang);
        pnCenter.add(pnRightBanHang);

        this.add(pnCardTabBanHang);
        this.add(pnCenter, BorderLayout.CENTER);
        loadDataTableSanPhamBan();
        txtTenSPBanHang.requestFocus();
        lblAnhSP.setIcon(getAnhSP(""));
    }

    private void loadDataComboboxLoaiBanSP() {
        cmbLoaiSPBanHang.addItem("0 - Chọn loại");
        ArrayList<LoaiSP> dsl = loaiBUS.getDanhSachLoai();

        for (LoaiSP loai : dsl) {
            if (!loai.getTenLoai().equalsIgnoreCase("Nguyên liệu")) {
                cmbLoaiSPBanHang.addItem(loai.getMaLoai() + " - " + loai.getTenLoai());
            }
        }
    }

    private void loadDataComboboxNhanVienBan() {
        ArrayList<NhanVien> dsnv = nvBUS.getDanhSachNhanVien();
        if (dsnv != null) {
            for (NhanVien nv : dsnv) {
                cmbNhanVienBan.addItem(nv.getMaNV() + " - " + nv.getHoTen());
            }
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Custom table">
    private void customTable(JTable tbl) {
        //======CUSTOM TABLE=======
        tbl.setFocusable(false);
        tbl.setIntercellSpacing(new Dimension(0, 0));
        tbl.setRowHeight(25);
        tbl.setSelectionBackground(new Color(50, 154, 114));
        tbl.setSelectionForeground(Color.white);
        tbl.setFont(new Font("Arial", Font.PLAIN, 16));

        JTableHeader header = tbl.getTableHeader();
        header.setBackground(new Color(242, 153, 74));
        header.setFont(new Font("Arial", Font.BOLD, 16));
        header.setOpaque(false);
        header.setForeground(Color.WHITE);
        header.setReorderingAllowed(false);
        ((DefaultTableCellRenderer) header.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
        //======/CUSTOM TABLE/=======
    }
//</editor-fold>

    DecimalFormat dcf = new DecimalFormat("###,###");

    private void loadDataTableSanPhamBan() {
        dtmSanPhamBan.setRowCount(0);

        String loai = cmbLoaiSPBanHang.getSelectedItem() + "";
        String loaiArr[] = loai.split("-");
        String loaiSP = loaiArr[0].trim();

        ArrayList<SanPham> dssp = null;
        if (loaiSP.equals("0")) {
            dssp = spBUS.getListSanPham();
        } else {
            dssp = spBUS.getSanPhamTheoLoai(loaiSP);
        }

        for (SanPham sp : dssp) {
            Vector vec = new Vector();
            vec.add(sp.getMaSP());
            vec.add(sp.getTenSP());
            vec.add(dcf.format(sp.getDonGia()));
            vec.add(dcf.format(sp.getSoLuong()));
            vec.add(sp.getDonViTinh());
            vec.add(sp.getHinhAnh());
            dtmSanPhamBan.addRow(vec);
        }
    }

    private void xuLyClickTblBanHang() {
        int row = tblBanHang.getSelectedRow();
        if (row > -1) {
            String ma = tblBanHang.getValueAt(row, 0) + "";
            String ten = tblBanHang.getValueAt(row, 1) + "";
            String donGia = tblBanHang.getValueAt(row, 2) + "";
            String anh = tblBanHang.getValueAt(row, 5) + "";
            int soLuong = Integer.parseInt(tblBanHang.getValueAt(row, 3) + "");

            SpinnerNumberModel modeSpinner = new SpinnerNumberModel(1, 1, soLuong, 1);
            spnSoLuongBanHang.setModel(modeSpinner);
            JFormattedTextField txtSpinner = ((JSpinner.NumberEditor) spnSoLuongBanHang.getEditor()).getTextField();
            ((NumberFormatter) txtSpinner.getFormatter()).setAllowsInvalid(false);
            txtSpinner.setEditable(false);
            txtSpinner.setHorizontalAlignment(JTextField.LEFT);

            txtMaSPBanHang.setText(ma);
            txtTenSPBanHang.setText(ten);
            txtDonGiaBanHang.setText(donGia);
            loadAnh(anh);
        }
    }

    private void loadAnh(String anh) {
        lblAnhSP.setIcon(getAnhSP(anh));
    }

    File fileAnhSP;

    private ImageIcon getAnhSP(String src) {
        src = src.trim().equals("") ? "default.png" : src;
        //Xử lý ảnh
        BufferedImage img = null;
        File fileImg = new File("image/SanPham/" + src);

        if (!fileImg.exists()) {
            src = "default.png";
            fileImg = new File("image/SanPham/" + src);
        }

        try {
            img = ImageIO.read(fileImg);
            fileAnhSP = new File("image/SanPham/" + src);
        } catch (IOException e) {
            fileAnhSP = new File("image/SanPham/default.png");
        }

        if (img != null) {
            Image dimg = img.getScaledInstance(200, 200, Image.SCALE_SMOOTH);

            return new ImageIcon(dimg);
        }
        return null;
    }

    private void xuLyThemVaoGioHang() {
        int row = tblBanHang.getSelectedRow();
        if (row < 0) {
            return;
        }

        String ma = txtMaSPBanHang.getText();
        String ten = txtTenSPBanHang.getText();
        String donGia = txtDonGiaBanHang.getText();
        int soLuong = Integer.parseInt(spnSoLuongBanHang.getValue() + "");
        int soLuongConLai = Integer.parseInt(tblBanHang.getValueAt(tblBanHang.getSelectedRow(), 3) + "");

        if (soLuong > soLuongConLai) {
            return;
        }

        txtMaSPBanHang.setText("");
        txtTenSPBanHang.setText("");
        txtDonGiaBanHang.setText("");
        spnSoLuongBanHang.setValue(0);

        int key = Integer.parseInt(ma);
        for (int i = 0; i < tblGioHang.getRowCount(); i++) {
            int maTbl = Integer.parseInt(tblGioHang.getValueAt(i, 0) + "");
            if (maTbl == key) {
                int soLuongAdd = Integer.parseInt(tblGioHang.getValueAt(i, 2) + "");
                soLuong += soLuongAdd;
                Vector vec = new Vector();
                vec.add(ma);
                vec.add(ten);
                vec.add(soLuong);
                vec.add(donGia);
                donGia = donGia.replace(",", "");
                int donGiaSP = Integer.parseInt(donGia);
                vec.add(dcf.format(soLuong * donGiaSP));
                tblGioHang.setValueAt(soLuong, i, 2);
                tblGioHang.setValueAt(dcf.format(soLuong * donGiaSP), i, 4);

                // cập nhật lại số lượng trong db
                spBUS.capNhatSoLuongSP(key, -soLuongAdd);
                loadDataTableSanPhamBan();
                return;
            }
        }

        Vector vec = new Vector();
        vec.add(ma);
        vec.add(ten);
        vec.add(soLuong);
        vec.add(donGia);
        donGia = donGia.replace(",", "");
        int donGiaSP = Integer.parseInt(donGia);
        vec.add(dcf.format(soLuong * donGiaSP));
        // cập nhật lại số lượng trong db
        spBUS.capNhatSoLuongSP(key, -soLuong);
        loadDataTableSanPhamBan();
        dtmGioHang.addRow(vec);
    }

    private void xuLyXoaSPGioHang() {
        int row = tblGioHang.getSelectedRow();
        if (row > -1) {
            int ma = Integer.parseInt(tblGioHang.getValueAt(row, 0) + "");
            int soLuong = Integer.parseInt("");
            System.out.println(tblGioHang.getValueAt(row, 3) + "");
            spBUS.capNhatSoLuongSP(ma, soLuong);
            loadDataTableSanPhamBan();
            dtmGioHang.removeRow(row);
        }
    }

    private void xuLyXuatHoaDonBanHang() {

    }

    private void xuLyClickTblGioHang() {
        int row = tblGioHang.getSelectedRow();
        if (row > -1) {
            String ma = tblGioHang.getValueAt(row, 0) + "";
            loadAnh(spBUS.getAnh(ma));
        }
    }

    private void addEventsBanHang() {
        lblTabbedBanHang.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                lblTabbedHoaDon.setIcon(tabbedDefault);
                lblTabbedBanHang.setIcon(tabbedSelected);
                cardBanHangGroup.show(pnCardTabBanHang, "1");
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

        lblTabbedHoaDon.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                lblTabbedHoaDon.setIcon(tabbedSelected);
                lblTabbedBanHang.setIcon(tabbedDefault);
                cardBanHangGroup.show(pnCardTabBanHang, "2");
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

        tblBanHang.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                xuLyClickTblBanHang();
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        tblGioHang.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                xuLyClickTblGioHang();
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

        btnThemVaoGio.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                xuLyThemVaoGioHang();
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

        btnXoaSPGioHang.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                xuLyXoaSPGioHang();
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

        btnXuatHoaDonSP.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                xuLyXuatHoaDonBanHang();
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

        cmbLoaiSPBanHang.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadDataTableSanPhamBan();
            }
        });

        txtTenSPBanHang.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xuLyTimKiemTheoTen();
            }
        });
    }

    private void xuLyTimKiemTheoTen() {
        String ten = txtTenSPBanHang.getText().toLowerCase();
        dtmSanPhamBan.setRowCount(0);
        ArrayList<SanPham> dssp = null;
        dssp = spBUS.getListSanPham();

        for (SanPham sp : dssp) {
            String tenSP = sp.getTenSP().toLowerCase();
            if (tenSP.contains(ten)) {
                Vector vec = new Vector();
                vec.add(sp.getMaSP());
                vec.add(sp.getTenSP());
                vec.add(dcf.format(sp.getDonGia()));
                vec.add(dcf.format(sp.getSoLuong()));
                vec.add(sp.getDonViTinh());
                vec.add(sp.getHinhAnh());
                dtmSanPhamBan.addRow(vec);
            }
        }
    }
}