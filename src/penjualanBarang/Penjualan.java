/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package penjualanBarang;
import com.mysql.jdbc.ResultSetMetaData;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import javax.swing.Timer;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import org.apache.poi.hssf.usermodel.*;

/**
 *
 * @author yatna
 */
public class Penjualan extends javax.swing.JFrame {
java.util.Date sekarang=new java.util.Date();
private final SimpleDateFormat simple=new SimpleDateFormat("dd MMMMMMMMMM yy",  Locale.getDefault());
private final String tanggal = simple.format(sekarang);
private Connection con;
private Statement stat;
private ResultSet res;
private String t;
Map param = new HashMap();
ResultSetMetaData rmd;
HSSFWorkbook wb = new HSSFWorkbook();
HSSFSheet sheet = wb.createSheet("new sheet");
HSSFFont font = wb.createFont();
    
    public Penjualan() {
        initComponents();
        unvisible(false);
        stok.setVisible(true);
        jLabel1.setText(tanggal);
        setJam();
        koneksi();
        Nota.setEnabled(false);
    }
    private void koneksi(){
try {
Class.forName("com.mysql.jdbc.Driver");
con=DriverManager.getConnection("jdbc:mysql://localhost:3306/penjualan", "root", "");
stat=con.createStatement();
} catch (ClassNotFoundException | SQLException e) {
JOptionPane.showMessageDialog(null, e);
}
}
    
    public final void setJam(){
ActionListener taskPerformer = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent evt) {
        String nol_jam = "", nol_menit = "",nol_detik = "";

        java.util.Date dateTime = new java.util.Date();
        int nilai_jam = dateTime.getHours();
        int nilai_menit = dateTime.getMinutes();
        int nilai_detik = dateTime.getSeconds();

        if(nilai_jam <= 9) nol_jam= "0";
        if(nilai_menit <= 9) nol_menit= "0";
        if(nilai_detik <= 9) nol_detik= "0";

        String jam = nol_jam + Integer.toString(nilai_jam);
        String menit = nol_menit + Integer.toString(nilai_menit);
        String detik = nol_detik + Integer.toString(nilai_detik);

        jLabel2.setText(jam+":"+menit+":"+detik+"");
        }
        };
        new Timer(1000, taskPerformer).start();
}
    private void IDotomatis1(){
        try { 
        res=stat.executeQuery("select max(id)from penjualan ");
        while(res.next()){
            int a = res.getInt(1);
                idpenjualan.setText(""+ Integer.toString(a+1));
        }
    } catch (SQLException e){
        JOptionPane.showMessageDialog(null, e);
    }
    }
    private void IDotomatis(){
        try { 
        res=stat.executeQuery("select max(id)from penjualan ");
        while(res.next()){
            int a = res.getInt(1);
                Nota.setText("NONT-"+ Integer.toString(a+1));
        }
    } catch (SQLException e){
        JOptionPane.showMessageDialog(null, e);
    }
    }
    private void tabel(){ 
DefaultTableModel tab= new DefaultTableModel();
  tab.addColumn("ID Rumah"); 
 tab.addColumn("Nama"); 
 tab.addColumn("Harga");
 tab.addColumn("Katagori Rumah");
 tab.addColumn("Stok");
 tab.addColumn("Gambar");

 
 jTable2.setModel(tab); 
 try{ res=stat.executeQuery("select * from tabel_rumah"); 
 while (res.next()) { 
         Object[] o=new Object[6];
         o[0]=res.getString("id_rumah");
         o[1]=res.getString("nama_rumah"); 
         o[2]=res.getString("harga_rumah");
         o[3]=res.getString("katagori_rumah");
         o[4]=res.getString("stok_rumah");
         o[5]=res.getString("gambar_rumah");
         tab.addRow(o);
 }
 }catch (SQLException e) { 
 JOptionPane.showMessageDialog(rootPane, e); 
 } 
}
    private void tabel1(){ 
DefaultTableModel tab= new DefaultTableModel();
  tab.addColumn("ID Rumah"); 
 tab.addColumn("Nama"); 
 tab.addColumn("Harga");
 tab.addColumn("Jumlah");
 tab.addColumn("Total");
 

 
 jTable1.setModel(tab); 
 try{ res=stat.executeQuery("select idBarang,nmBarang,harga,jml,total from detailjual where nota='"+Nota.getText()+"'"); 
 while (res.next()) { 
         Object[] o=new Object[5];
         o[0]=res.getString("idBarang");
         o[1]=res.getString("nmBarang"); 
         o[2]=res.getString("harga");
         o[3]=res.getString("jml");
         o[4]=res.getString("total");
         tab.addRow(o);
        int jumlahBaris = jTable1.getRowCount();
        int totalBiaya = 0;
        int totaal = 0;
        for (int i=0; i<jumlahBaris; i++){
            totaal = Integer.parseInt(jTable1.getValueAt(i, 4).toString());
            totalBiaya = totalBiaya + totaal ;
    }
       totale.setText(String.valueOf(totalBiaya));
        
 }
 }catch (NumberFormatException | SQLException e) { 
 JOptionPane.showMessageDialog(rootPane, e); 
 } 
}
     private void tabel2(){ 
DefaultTableModel tab= new DefaultTableModel();
  tab.addColumn("ID Rumah"); 
 tab.addColumn("Nama"); 
 tab.addColumn("Harga");
 tab.addColumn("Jumlah");
 tab.addColumn("Total");
 

 
 jTable1.setModel(tab); 
 try{ res=stat.executeQuery("select idBarang,nmBarang,harga,jml,total from detailjual where nota='"+Nota.getText()+"'"); 
 while (res.next()) { 
         Object[] o=new Object[5];
         o[0]=res.getString("idBarang");
         o[1]=res.getString("nmBarang"); 
         o[2]=res.getString("harga");
         o[3]=res.getString("jml");
         o[4]=res.getString("total");
         tab.addRow(o);
        int jumlahBaris = jTable1.getRowCount();
        int totalBiaya = 0;
        int totaal = 0;
        for (int i=0; i<jumlahBaris; i++){
            totaal = Integer.parseInt(jTable1.getValueAt(i, 4).toString());
            totalBiaya = totaal - totalBiaya ;
    }
       totale.setText(String.valueOf(totalBiaya));
        
 }
 }catch (NumberFormatException | SQLException e) { 
 JOptionPane.showMessageDialog(rootPane, e); 
 } 
}
    private void unvisible(boolean a){
        idSuplayer.setEnabled(a);
        namaSuplayer.setEnabled(a);
        idPelanggan.setEnabled(a);
        namaPelanggan.setEnabled(a);
        kode.setEnabled(a);
        cari.setEnabled(a);
        namaBrg.setEnabled(a);
        hargaBrg.setEnabled(a);
        jumlah.setEnabled(a);
        tambah.setEnabled(a);
        kurang.setEnabled(a);
        
    }
   


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        idpenjualan = new javax.swing.JTextField();
        frmRumah = new javax.swing.JDialog();
        jPanel2 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        stok = new javax.swing.JTextField();
        kode2 = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        idSuplayer = new javax.swing.JTextField();
        namaSuplayer = new javax.swing.JTextField();
        idPelanggan = new javax.swing.JTextField();
        namaPelanggan = new javax.swing.JTextField();
        kode = new javax.swing.JTextField();
        namaBrg = new javax.swing.JTextField();
        hargaBrg = new javax.swing.JTextField();
        jumlah = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        totale = new javax.swing.JTextField();
        bayar = new javax.swing.JTextField();
        kembalian = new javax.swing.JTextField();
        cari = new javax.swing.JButton();
        Nota = new javax.swing.JTextField();
        tambah = new javax.swing.JButton();
        kurang = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();

        idpenjualan.setEditable(false);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("Data Rumah");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(111, Short.MAX_VALUE)
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(83, 83, 83))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
        );

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        javax.swing.GroupLayout frmRumahLayout = new javax.swing.GroupLayout(frmRumah.getContentPane());
        frmRumah.getContentPane().setLayout(frmRumahLayout);
        frmRumahLayout.setHorizontalGroup(
            frmRumahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(frmRumahLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(frmRumahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        frmRumahLayout.setVerticalGroup(
            frmRumahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(frmRumahLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        stok.setText("Stok");

        kode2.setText("jTextField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(153, 255, 153));
        jPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(5, 5, 5, 5, new java.awt.Color(204, 255, 204)));

        jLabel1.setText("jLabel1");

        jLabel2.setText("jLabel2");

        jLabel3.setText("No Nota");

        jLabel4.setText("ID Suplayer");

        jLabel5.setText("Nama Suplayer");

        jLabel6.setText("ID Pelanggan");

        jButton1.setForeground(new java.awt.Color(255, 0, 204));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/add-icon.png"))); // NOI18N
        jButton1.setText("Tambah Penjualan");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel7.setText("Nama Pelanggan");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jLabel12.setText("Kode Barang");

        jLabel13.setText("Nama Barang");

        jLabel14.setText("Harga");

        jLabel8.setText("Jumlah");

        idSuplayer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                idSuplayerKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                idSuplayerKeyTyped(evt);
            }
        });

        namaSuplayer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                namaSuplayerActionPerformed(evt);
            }
        });

        idPelanggan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                idPelangganKeyPressed(evt);
            }
        });

        jLabel15.setText("Total");

        jLabel16.setText("Bayar");

        jLabel17.setText("Kembalian");

        totale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                totaleActionPerformed(evt);
            }
        });

        bayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bayarActionPerformed(evt);
            }
        });
        bayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                bayarKeyPressed(evt);
            }
        });

        cari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/search-icon-red-hi.png"))); // NOI18N
        cari.setText("Cari");
        cari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cariActionPerformed(evt);
            }
        });

        Nota.setEditable(false);
        Nota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NotaActionPerformed(evt);
            }
        });

        tambah.setText("+");
        tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tambahActionPerformed(evt);
            }
        });

        kurang.setText("-");
        kurang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kurangActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/back.png"))); // NOI18N
        jButton2.setText("Kembali");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("REPORT");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(0, 153, 102));

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(51, 51, 51));
        jLabel9.setText("Transaksi Penjualan");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(217, 217, 217)
                .addComponent(jLabel9)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel9)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel6)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3))
                        .addGap(80, 80, 80)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(idPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(namaPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(namaSuplayer, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(idSuplayer)
                                .addComponent(Nota, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton2)
                                .addGap(18, 18, 18)
                                .addComponent(jButton1)
                                .addGap(18, 18, 18)
                                .addComponent(jButton3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel17)
                                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(53, 53, 53)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(kembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(totale)
                                        .addComponent(bayar, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(kode, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cari, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(namaBrg, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(13, 13, 13)
                                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(hargaBrg, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(jumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(tambah))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(41, 41, 41)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel1)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel2)))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(kurang)))
                        .addGap(18, 18, 18))))
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Nota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(idSuplayer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(namaSuplayer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(idPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(namaPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel12)
                                .addComponent(jLabel13))
                            .addComponent(jLabel14))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(kode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(namaBrg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(hargaBrg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cari)
                                .addComponent(jumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(tambah)
                                .addComponent(kurang))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(34, 34, 34)))
                .addGap(27, 27, 27)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(totale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(jButton2)
                    .addComponent(jButton1)
                    .addComponent(jButton3))
                .addGap(12, 12, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(kembalian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        IDotomatis1();
        IDotomatis();
        unvisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void bayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bayarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bayarActionPerformed

    private void cariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cariActionPerformed
        // TODO add your handling code here:
        tabel();
        frmRumah.setBounds(300, 300, 472, 390);
        frmRumah.setVisible(true);
    }//GEN-LAST:event_cariActionPerformed

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        // TODO add your handling code here:
        int i=jTable2.getSelectedRow();
        if(i==-1)
        {
            return;
        }
        
                String id=(String) jTable2.getValueAt(i, 0);
                kode.setText(id);
                String nama=(String) jTable2.getValueAt(i, 1);
                namaBrg.setText(nama);
                String harga=(String) jTable2.getValueAt(i, 2);
                hargaBrg.setText(harga);
                String stoks=(String) jTable2.getValueAt(i, 4);
                stok.setText(stoks);
                frmRumah.setVisible(false);
                jumlah.requestFocus();
    }//GEN-LAST:event_jTable2MouseClicked

    private void idSuplayerKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_idSuplayerKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==10){
        try {
            res=stat.executeQuery("select * from admin where "+ "id_admin='" +idSuplayer.getText()
                +"'" ); while (res.next())
            { namaSuplayer.setText(res.getString("nama"));
            }
                idPelanggan.requestFocus();
        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }
        }
        
    }//GEN-LAST:event_idSuplayerKeyPressed

    private void idSuplayerKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_idSuplayerKeyTyped
        // TODO add your handling code here:
        
    }//GEN-LAST:event_idSuplayerKeyTyped

    private void idPelangganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_idPelangganKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==10){
        try {
            res=stat.executeQuery("select * from pelanggan where "+ "id_pelanggan='" +idPelanggan.getText()
                +"'" ); while (res.next())
            { namaPelanggan.setText(res.getString("nama"));
            }
                kode.requestFocus();
        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }
        }
        
    }//GEN-LAST:event_idPelangganKeyPressed

    private void kurangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kurangActionPerformed
        // TODO add your handling code here:
        try{
        int i=jTable1.getSelectedRow();
        if(i==-1)
        {
            return;
        }
                int jumlah2 = Integer.parseInt(jTable1.getValueAt(i, 3).toString());
                int stoek = Integer.parseInt(stok.getText());
                int hasil = 0;
                hasil = jumlah2+stoek ;
                stat.executeUpdate("update tabel_rumah set "
                            +"stok_rumah='"+hasil+"'"
                            +" where " + "id_rumah='"+kode2.getText()+"'");
                String kode1 =(String) jTable1.getValueAt(i, 0);
                kode2.setText(kode1);
                stat.executeUpdate("delete from detailjual where "
                        + "idBarang='"+kode2.getText()
                        +"'" );
                tabel2();
        }catch(NumberFormatException | SQLException a) {
            JOptionPane.showMessageDialog(null, "terjadi kesalahan"+a);
        }      
    }//GEN-LAST:event_kurangActionPerformed

    private void tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tambahActionPerformed
        // TODO add your handling code here:
        try{
            if(kode.getText().equals("") || namaBrg.getText().equals("") || hargaBrg.getText().equals("") || jumlah.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Silahkan lengkapi data barang dahulu");
            }else{
                int jml=Integer.parseInt(jumlah.getText());
                int stoke=Integer.parseInt(stok.getText());
                int hasil = 0;
                hasil = stoke-jml;
                if(stoke<jml){
                    JOptionPane.showMessageDialog(null, "Stok Barang kurang");
                }else{
                    stat.executeUpdate("update tabel_rumah set "
                            +"stok_rumah='"+hasil+"'"
                            +" where " + "id_rumah='"+kode.getText()+"'");
                    int totals=0;
                    int harga=Integer.parseInt(hargaBrg.getText());
                    totals=jml*harga;
                    stat.executeUpdate("insert into detailjual values (" 
                            + "'" + Nota.getText()+"'," 
                            + "'" + kode.getText()+"'," 
                            + "'" + namaBrg.getText()+"',"
                            + "'" + hargaBrg.getText()+"',"
                            + "'" + jumlah.getText()+"',"
                            + "'" + totals + "')");
                }
                tabel1();
                kode.setText("");
                namaBrg.setText("");
                hargaBrg.setText("");
                jumlah.setText("");
                stok.setText(String.valueOf(hasil));
            }
        }catch (HeadlessException | NumberFormatException | SQLException e){
            JOptionPane.showMessageDialog(null, "terjadi kesalahan"+e);
        }
    }//GEN-LAST:event_tambahActionPerformed

    private void namaSuplayerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_namaSuplayerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_namaSuplayerActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        new Home().setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void bayarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_bayarKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==10){
        int thoetal = Integer.parseInt(totale.getText());
        int bayare = Integer.parseInt(bayar.getText());
        if(bayare<thoetal){
            JOptionPane.showMessageDialog(null, "Uang anda kurang");
        }else{
            int kembaliane = 0;
            kembaliane=bayare-thoetal;
            kembalian.setText(String.valueOf(kembaliane));
        }
    }
    }//GEN-LAST:event_bayarKeyPressed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        DateFormat tgl=new SimpleDateFormat("yyyy/MM/dd");
        String htgl=tgl.format(Calendar.getInstance().getTime());
        int baris=jTable1.getRowCount();
        try{
            if(Nota.getText().equals("")||namaPelanggan.getText().equals("")||baris==0||bayar.getText().equals("")||kembalian.getText().equals("")){
                JOptionPane.showMessageDialog(null, "lengkapi inputan terlebih dahulu");
            }else{
                stat.executeUpdate("insert into penjualan values (" 
                    + "'" + idpenjualan.getText()+"'," 
                    + "'" + Nota.getText()+"'," 
                    + "'" + htgl+ "',"
                    + "'" + namaPelanggan.getText()+ "',"
                    + "'" + totale.getText()+"',"
                    + "'" + bayar.getText()+"',"
                    + "'" + kembalian.getText()+ "')");
                int pesan=JOptionPane.showConfirmDialog(null, "Cetak Nota","Cetak",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                if(pesan==JOptionPane.YES_OPTION){
//                    File reporFile = new File("report1.jrxml");
//                    JasDes=JRXmlLoader.load(report);
//                    param.put("penjualan_nota",Nota.getText());
//                    JasRep = JasperCompileManager.compileReport(JasDes);
//                    JasPri = JasperFillManager.fillReport(JasRep, param, con);
//                    JasperViewer.viewReport(JasPri,false);
                    font.setFontHeightInPoints((short)10);
                    font.setFontName("Arial");
                    font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
                    HSSFCellStyle style = wb.createCellStyle();
                    style.setFont(font);
                    res=stat.executeQuery("select * from penjualan where "+ "id='"+idpenjualan.getText()+"'");
                    rmd=(ResultSetMetaData) res.getMetaData();
                    HSSFRow row = sheet.createRow((short)0);
                    for (int i=0; i<rmd.getColumnCount(); i++){
                        row.createCell((short)i).setCellValue(rmd.getColumnName(i+1));
                        row.getCell((short)i).setCellStyle(style);
                    }
                    int i=1;
                    int col = rmd.getColumnCount();
                    while(res.next()){
                        row = sheet.createRow((short)i++);
                        for (int j=0; j<col; j++){
                            row.createCell((short)j).setCellValue(res.getString(j+1));
                        }
                    }
                    try (FileOutputStream fileOut = new
                            FileOutputStream(System.getProperty("user.dir")+"/excel/"+"coba.xls")) {
                        wb.write(fileOut);
                    }
                    JOptionPane.showMessageDialog(null, "Berhasil Simpan Excel");
                }
            }
        }catch(HeadlessException | IOException | SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void NotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NotaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NotaActionPerformed

    private void totaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_totaleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_totaleActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Penjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Penjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Penjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Penjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Penjualan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Nota;
    private javax.swing.JTextField bayar;
    private javax.swing.JButton cari;
    private javax.swing.JDialog frmRumah;
    private javax.swing.JTextField hargaBrg;
    private javax.swing.JTextField idPelanggan;
    private javax.swing.JTextField idSuplayer;
    private javax.swing.JTextField idpenjualan;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jumlah;
    private javax.swing.JTextField kembalian;
    private javax.swing.JTextField kode;
    private javax.swing.JTextField kode2;
    private javax.swing.JButton kurang;
    private javax.swing.JTextField namaBrg;
    private javax.swing.JTextField namaPelanggan;
    private javax.swing.JTextField namaSuplayer;
    private javax.swing.JTextField stok;
    private javax.swing.JButton tambah;
    private javax.swing.JTextField totale;
    // End of variables declaration//GEN-END:variables
}
