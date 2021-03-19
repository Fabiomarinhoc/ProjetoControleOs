/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.infox.telas;

import java.sql.*;
import br.com.infox.dal.ModuloConexao;
import java.util.HashMap;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Fabio
 */
public class TelaOs extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    // armazenar radiobutton
    private String tipo;

    /**
     * Creates new form TelaOs
     */
    public TelaOs() {
        initComponents();

        conexao = ModuloConexao.conector();

    }

    private void pesquisar_cliente() {
        String sql = "select idcliente as Id,nomecliente as Nome,fonecli as Fone from tbclientes where nomecliente like ? ";

        try {

            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtPesq.getText() + "%");
            rs = pst.executeQuery();
            tblOs.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void setar_campo() {
        int setar = tblOs.getSelectedRow();
        txtIdOs.setText(tblOs.getModel().getValueAt(setar, 0).toString());

    }

    //Método cadastrar os
    private void cadastrar_os() {

        String sql = "insert into tbos (tipo,situacao,equipamento,defeito, servico,tecnico,valor,idcliente) values ( ?,?,?,?,?,?,?,?)";

        try {

            pst = conexao.prepareStatement(sql);
            pst.setString(1, tipo);
            pst.setString(2, comboSit.getSelectedItem().toString());
            pst.setString(3, txtEquipa.getText());
            pst.setString(4, txtDef.getText());
            pst.setString(5, txtSer.getText());
            pst.setString(6, txtTec.getText());
            //replace substitui "," por "."
            pst.setString(7, txtValor.getText().replace(",", "."));
            pst.setString(8, txtIdOs.getText());

            //Validação campos obrigatorios
            if ((txtIdOs.getText().isEmpty()) || (txtEquipa.getText().isEmpty()) || (txtDef.getText().isEmpty())) {

                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios");

            } else {
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "O.S emitida com sucesso!");

                    txtEquipa.setText(null);
                    txtDef.setText(null);
                    txtSer.setText(null);
                    txtTec.setText(null);
                    txtValor.setText(null);
                    txtIdOs.setText(null);

                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    private void pesquisar_os() {
        //criando caixa d eentrada

        String num_os = JOptionPane.showInputDialog("Número da O.S");
        String sql = "select * from tbos where os =" + num_os;

        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                txtNOs.setText(rs.getString(1));
                txtData.setText(rs.getString(2));
                //setando o radio button

                String rbtTipo = rs.getString(3);
                if (rbtTipo.equals("OS")) {

                    rbtnOs.setSelected(true);
                    tipo = "OS";

                } else {

                    rbtnOr.setSelected(true);
                    tipo = "ORÇAMENTO";

                }

                comboSit.setSelectedItem(rs.getString(4));
                txtEquipa.setText(rs.getString(5));
                txtDef.setText(rs.getString(6));
                txtSer.setText(rs.getString(7));
                txtTec.setText(rs.getString(8));
                txtValor.setText(rs.getString(9));
                txtIdOs.setText(rs.getString(10));

                btnOcr.setEnabled(false);
                txtPesq.setEnabled(false);
                tblOs.setVisible(false);

            } else {

                JOptionPane.showMessageDialog(null, "O.S não encontrada");
            }
        } catch (java.sql.SQLSyntaxErrorException e) {
            JOptionPane.showMessageDialog(null, "O.S Inválida!");
            //System.out.println(e); capturar a mensagem
        } catch (Exception e2) {
            JOptionPane.showMessageDialog(null, e2);
        }

    }

    //Método para alterar OS
    private void alterar_os() {
        String sql = "update tbos set tipo =?,situacao =?, equipamento = ?, defeito = ?, servico = ?, tecnico = ?, valor = ? where os = ?";

        try {

            pst = conexao.prepareStatement(sql);
            pst.setString(1, tipo);
            pst.setString(2, comboSit.getSelectedItem().toString());
            pst.setString(3, txtEquipa.getText());
            pst.setString(4, txtDef.getText());
            pst.setString(5, txtSer.getText());
            pst.setString(6, txtTec.getText());
            //replace substitui "," por "."
            pst.setString(7, txtValor.getText().replace(",", "."));
            pst.setString(8, txtNOs.getText());

            //Validação campos obrigatorios
            if ((txtIdOs.getText().isEmpty()) || (txtEquipa.getText().isEmpty()) || (txtDef.getText().isEmpty())) {

                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios");

            } else {
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "O.S alterada com sucesso!");

                    txtNOs.setText(null);
                    txtEquipa.setText(null);
                    txtDef.setText(null);
                    txtSer.setText(null);
                    txtTec.setText(null);
                    txtValor.setText(null);
                    txtIdOs.setText(null);
                    txtData.setText(null);
                    //balitar objetos

                    btnOcr.setEnabled(true);
                    txtPesq.setEnabled(true);
                    tblOs.setVisible(true);

                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    //Método para excluir uma OS
    private void excluir_os() {
        int conrfima = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluír essa O.S", "Atenção", JOptionPane.YES_NO_OPTION);

        if (conrfima == JOptionPane.YES_OPTION) {

            String sql = " delete from tbos where os = ?";

            try {

                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtNOs.getText());
                int apagado = pst.executeUpdate();
                if (apagado > 0) {
                    JOptionPane.showMessageDialog(null, "O.S excluída com sucesso!");

                    txtNOs.setText(null);
                    txtEquipa.setText(null);
                    txtDef.setText(null);
                    txtSer.setText(null);
                    txtTec.setText(null);
                    txtValor.setText(null);
                    txtIdOs.setText(null);
                    txtData.setText(null);
                    //habilitar objetos

                    btnOcr.setEnabled(true);
                    txtPesq.setEnabled(true);
                    tblOs.setVisible(true);
                }

            } catch (Exception e) {

                JOptionPane.showMessageDialog(null, e);
            }

        }

    }

    //Método para imprimir OS
    private void imprimir_os() {

        int confirma = JOptionPane.showConfirmDialog(null, "Confirma a emissão desta OS", "Atenção", JOptionPane.YES_NO_OPTION);

        if (confirma == JOptionPane.YES_OPTION) {
            //imprimindo relatório

            try {
                //Usando a classe Hashmap para criar um filtro
                HashMap filtro = new HashMap();
                
                filtro.put("os",Integer.parseInt(txtNOs.getText()));
                
                //jasperprint utilizando framework para impressão

                JasperPrint print = JasperFillManager.fillReport("C:/reports/RelatorioOS.jasper", filtro, conexao);

                //a linha abaixo exibe o relatório
                JasperViewer.viewReport(print, false);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        painelOs = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtNOs = new javax.swing.JTextField();
        txtData = new javax.swing.JTextField();
        rbtnOr = new javax.swing.JRadioButton();
        rbtnOs = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        comboSit = new javax.swing.JComboBox<>();
        painelCl = new javax.swing.JPanel();
        txtPesq = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtIdOs = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblOs = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtEquipa = new javax.swing.JTextField();
        txtDef = new javax.swing.JTextField();
        txtSer = new javax.swing.JTextField();
        txtTec = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtValor = new javax.swing.JTextField();
        btnOcr = new javax.swing.JButton();
        btnOpes = new javax.swing.JButton();
        btnOalt = new javax.swing.JButton();
        btnOdel = new javax.swing.JButton();
        btnOimp = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Area ordem de serviço");
        setPreferredSize(new java.awt.Dimension(648, 567));
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameOpened(evt);
            }
        });

        painelOs.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setText("N° OS");

        jLabel2.setText("Data");

        txtNOs.setEditable(false);
        txtNOs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNOsActionPerformed(evt);
            }
        });

        txtData.setEditable(false);
        txtData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDataActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbtnOr);
        rbtnOr.setText("Orçamento");
        rbtnOr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnOrActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbtnOs);
        rbtnOs.setText("Ordem de serviço");
        rbtnOs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnOsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout painelOsLayout = new javax.swing.GroupLayout(painelOs);
        painelOs.setLayout(painelOsLayout);
        painelOsLayout.setHorizontalGroup(
            painelOsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelOsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelOsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painelOsLayout.createSequentialGroup()
                        .addComponent(rbtnOr)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                        .addComponent(rbtnOs)
                        .addGap(12, 12, 12))
                    .addGroup(painelOsLayout.createSequentialGroup()
                        .addGroup(painelOsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(txtNOs, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addGroup(painelOsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        painelOsLayout.setVerticalGroup(
            painelOsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelOsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelOsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(painelOsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNOs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(painelOsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbtnOr)
                    .addComponent(rbtnOs))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jLabel3.setText("Situação");

        comboSit.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Na bancada", "Entrega ok", "Orçamento reprovado", "Aguardando aprovação", "Aguardando peça", "Abandonado pelo cliente", "Retornou" }));
        comboSit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboSitActionPerformed(evt);
            }
        });

        painelCl.setBorder(javax.swing.BorderFactory.createTitledBorder("Cliente"));

        txtPesq.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesqKeyReleased(evt);
            }
        });

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/lupa1.jpg"))); // NOI18N

        txtIdOs.setEditable(false);
        txtIdOs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdOsActionPerformed(evt);
            }
        });

        jLabel5.setText("*Id");

        tblOs.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Id", "Nome", "Fone"
            }
        ));
        tblOs.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblOsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblOs);

        javax.swing.GroupLayout painelClLayout = new javax.swing.GroupLayout(painelCl);
        painelCl.setLayout(painelClLayout);
        painelClLayout.setHorizontalGroup(
            painelClLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelClLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(painelClLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelClLayout.createSequentialGroup()
                        .addComponent(txtPesq, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtIdOs, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(51, 51, 51))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelClLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(62, 62, 62))))
        );
        painelClLayout.setVerticalGroup(
            painelClLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelClLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelClLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(txtPesq, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(painelClLayout.createSequentialGroup()
                        .addGroup(painelClLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtIdOs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(3, 3, 3)))
                .addGap(34, 34, 34)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel6.setText("*Equipamento");

        jLabel7.setText("*Defeito");

        jLabel8.setText("Serviço");

        jLabel9.setText("Técnico");

        txtEquipa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEquipaActionPerformed(evt);
            }
        });

        jLabel10.setText("Valor total");

        txtValor.setText("0");

        btnOcr.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/create.png"))); // NOI18N
        btnOcr.setToolTipText("Criar");
        btnOcr.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOcr.setPreferredSize(new java.awt.Dimension(80, 80));
        btnOcr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOcrActionPerformed(evt);
            }
        });

        btnOpes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/pesquisar.png"))); // NOI18N
        btnOpes.setToolTipText("Pesquisar");
        btnOpes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOpes.setPreferredSize(new java.awt.Dimension(80, 80));
        btnOpes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpesActionPerformed(evt);
            }
        });

        btnOalt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/alterar1.jpg"))); // NOI18N
        btnOalt.setToolTipText("Alterar");
        btnOalt.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOalt.setPreferredSize(new java.awt.Dimension(80, 80));
        btnOalt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOaltActionPerformed(evt);
            }
        });

        btnOdel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/deletar.png"))); // NOI18N
        btnOdel.setToolTipText("Deletar");
        btnOdel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOdel.setPreferredSize(new java.awt.Dimension(80, 80));
        btnOdel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOdelActionPerformed(evt);
            }
        });

        btnOimp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/Imprimir1.png"))); // NOI18N
        btnOimp.setToolTipText("Imprimir Os");
        btnOimp.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOimp.setPreferredSize(new java.awt.Dimension(80, 80));
        btnOimp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOimpActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtDef, javax.swing.GroupLayout.DEFAULT_SIZE, 517, Short.MAX_VALUE)
                            .addComponent(txtSer)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(jLabel9)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnOcr, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(27, 27, 27)
                                .addComponent(btnOpes, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnOalt, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnOdel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnOimp, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 66, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtTec, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtValor)))))
                .addGap(109, 109, 109))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtEquipa, javax.swing.GroupLayout.PREFERRED_SIZE, 517, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(comboSit, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(painelOs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(painelCl, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(painelOs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(comboSit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(painelCl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtEquipa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtDef, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(txtSer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(txtTec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10)))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnOcr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(btnOalt, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnOdel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnOpes, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnOimp, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(46, Short.MAX_VALUE))
        );

        setBounds(0, 0, 640, 477);
    }// </editor-fold>//GEN-END:initComponents

    private void txtNOsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNOsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNOsActionPerformed

    private void txtDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDataActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDataActionPerformed

    private void txtIdOsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdOsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdOsActionPerformed

    private void txtEquipaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEquipaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEquipaActionPerformed

    private void btnOaltActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOaltActionPerformed
        // Chamando método alterar os

        alterar_os();
    }//GEN-LAST:event_btnOaltActionPerformed

    private void btnOimpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOimpActionPerformed
        //Chamando o método imprimir o.s
        
        imprimir_os();
    }//GEN-LAST:event_btnOimpActionPerformed

    private void txtPesqKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesqKeyReleased
        // chamar método

        pesquisar_cliente();
    }//GEN-LAST:event_txtPesqKeyReleased

    private void tblOsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblOsMouseClicked
        // chamando o evento setar campos

        setar_campo();
    }//GEN-LAST:event_tblOsMouseClicked

    private void rbtnOrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnOrActionPerformed
        // atribuindo um texto a a variavel tipo

        tipo = "ORÇAMENTO";


    }//GEN-LAST:event_rbtnOrActionPerformed

    private void rbtnOsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnOsActionPerformed
        // atribuindo um texto a a variavel tipo

        tipo = "OS";
    }//GEN-LAST:event_rbtnOsActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        //Ao abrir o forme marca o radiobutton
        rbtnOr.setSelected(true);
        tipo = "ORÇAMENTO";

    }//GEN-LAST:event_formInternalFrameOpened

    private void comboSitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboSitActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboSitActionPerformed

    private void btnOcrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOcrActionPerformed
        // chamar método emitir os

        cadastrar_os();
    }//GEN-LAST:event_btnOcrActionPerformed

    private void btnOpesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpesActionPerformed
        //chamando método pesquisar os

        pesquisar_os();


    }//GEN-LAST:event_btnOpesActionPerformed

    private void btnOdelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOdelActionPerformed
        // Chamando método exluir os

        excluir_os();
    }//GEN-LAST:event_btnOdelActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnOalt;
    private javax.swing.JButton btnOcr;
    private javax.swing.JButton btnOdel;
    private javax.swing.JButton btnOimp;
    private javax.swing.JButton btnOpes;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> comboSit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel painelCl;
    private javax.swing.JPanel painelOs;
    private javax.swing.JRadioButton rbtnOr;
    private javax.swing.JRadioButton rbtnOs;
    private javax.swing.JTable tblOs;
    private javax.swing.JTextField txtData;
    private javax.swing.JTextField txtDef;
    private javax.swing.JTextField txtEquipa;
    private javax.swing.JTextField txtIdOs;
    private javax.swing.JTextField txtNOs;
    private javax.swing.JTextField txtPesq;
    private javax.swing.JTextField txtSer;
    private javax.swing.JTextField txtTec;
    private javax.swing.JTextField txtValor;
    // End of variables declaration//GEN-END:variables
}
