/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.infox.telas;

import java.sql.*;
import br.com.infox.dal.ModuloConexao;
import java.util.HashSet;
import javax.swing.JOptionPane;

// a linha abaixo importa recurso da biblioteca rs2xml.jar
import net.proteanit.sql.DbUtils;

/**
 *
 * @author Fabio
 */
public class TelaCliente extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * Creates new form TelaCliente
     */
    public TelaCliente() {
        initComponents();
        conexao = ModuloConexao.conector();
    }
    //Método para adicionar clientes

    private void adicionar() {
        String sql = "insert into tbclientes(nomecliente,enderecl,fonecli,email)values(?,?,?,?)";

        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtCNome.getText());
            pst.setString(2, txtCEnd.getText());
            pst.setString(3, txtCTel.getText());
            pst.setString(4, txtCEmail.getText());

            //Validação campos vazios
            if ((txtCNome.getText().isEmpty()) || (txtCTel.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha os campos obrigatórios");

            } else {

                // A linha abaixo atualiza a tabela de cliente
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Cliente adicionado com sucesso");

                    txtCNome.setText(null);
                    txtCEnd.setText(null);
                    txtCTel.setText(null);
                    txtCEmail.setText(null);
                    txtId.setText(null);

                }

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    //pesquisar nome com filtros
    private void pesquisar_cliente() {
        String sql = "select * from tbclientes where nomecliente like ?";

        try {
            pst = conexao.prepareStatement(sql);
            //passando o conteudo da caixa de pesquisa parao interroga

            pst.setString(1, txtCpesq.getText() + "%");
            rs = pst.executeQuery();
            //a linha abaixo usa biblioteca rs2xml.jar preencher  a tabela

            tblcl.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, e);
        }

    }
    //Método para setar o conteudo para os campos

    public void setar_campos() {
        int setar = tblcl.getSelectedRow();
        txtId.setText(tblcl.getModel().getValueAt(setar, 0).toString());
        txtCNome.setText(tblcl.getModel().getValueAt(setar, 1).toString());
        txtCEnd.setText(tblcl.getModel().getValueAt(setar, 2).toString());
        txtCTel.setText(tblcl.getModel().getValueAt(setar, 3).toString());
        txtCEmail.setText(tblcl.getModel().getValueAt(setar, 4).toString());

        // A linha abaixo desabilita o método adicionar
        btnCCreate.setEnabled(false);

    }

    private void alterar() {
        String sql = "update tbclientes set nomecliente=?,enderecl =?,fonecli = ?,email =? where idcliente= ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtCNome.getText());
            pst.setString(2, txtCEnd.getText());
            pst.setString(3, txtCTel.getText());
            pst.setString(4, txtCEmail.getText());
            pst.setString(5, txtId.getText());

            if (txtCNome.getText().isEmpty() || (txtCTel.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha os campos obrigatórios");

            } else {
                int adicionado = pst.executeUpdate();

                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Dados alterados com sucesso!");
                    txtCNome.setText(null);
                    txtCEnd.setText(null);
                    txtCTel.setText(null);
                    txtCEmail.setText(null);
                    txtId.setText(null);

                    btnCCreate.setEnabled(true);

                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void remover(){
        
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover este cliente?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "delete from tbclientes where idcliente = ?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtId.getText());
                int apagado = pst.executeUpdate();

                if (apagado > 0) {
                    JOptionPane.showMessageDialog(null, "Cliente removido com sucesso");

                    txtCNome.setText(null);
                    txtCEnd.setText(null);
                    txtCTel.setText(null);
                    txtCEmail.setText(null);
                    txtId.setText(null);
                    btnCCreate.setEnabled(true);

                }

            } catch (Exception e) {

                JOptionPane.showMessageDialog(null, e);
            }

        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content1 of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jRadioButton1 = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtCNome = new javax.swing.JTextField();
        txtCEnd = new javax.swing.JTextField();
        txtCTel = new javax.swing.JTextField();
        txtCEmail = new javax.swing.JTextField();
        txtCpesq = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblcl = new javax.swing.JTable();
        btnCCreate = new javax.swing.JButton();
        btnCAlterar = new javax.swing.JButton();
        btnCDeletar = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();

        jRadioButton1.setText("jRadioButton1");

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Clientes");
        setPreferredSize(new java.awt.Dimension(648, 567));

        jLabel1.setText("*Nome");

        jLabel2.setText("Endereço");

        jLabel3.setText("*Telefone");

        jLabel4.setText("Email");

        jLabel5.setText("*Campos Obrigatórios");

        txtCNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCNomeActionPerformed(evt);
            }
        });

        txtCEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCEmailActionPerformed(evt);
            }
        });

        txtCpesq.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCpesqActionPerformed(evt);
            }
        });
        txtCpesq.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCpesqKeyReleased(evt);
            }
        });

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/lupa1.jpg"))); // NOI18N

        tblcl.setModel(new javax.swing.table.DefaultTableModel(
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
        tblcl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblclMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblcl);

        btnCCreate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/create.png"))); // NOI18N
        btnCCreate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCCreateActionPerformed(evt);
            }
        });

        btnCAlterar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/alterar1.jpg"))); // NOI18N
        btnCAlterar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCAlterarActionPerformed(evt);
            }
        });

        btnCDeletar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/deletar.png"))); // NOI18N
        btnCDeletar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCDeletar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCDeletarActionPerformed(evt);
            }
        });

        jLabel7.setText("Cod.");

        txtId.setEnabled(false);
        txtId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtCpesq, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addGap(322, 322, 322))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel3)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel1)
                                        .addComponent(jLabel2))
                                    .addComponent(jLabel7))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtCNome, javax.swing.GroupLayout.PREFERRED_SIZE, 473, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtCTel, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtCEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtCEnd, javax.swing.GroupLayout.PREFERRED_SIZE, 417, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 584, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(96, 96, 96)
                        .addComponent(btnCCreate, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(99, 99, 99)
                        .addComponent(btnCAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(99, 99, 99)
                        .addComponent(btnCDeletar, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(txtCpesq, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtCNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtCEnd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(txtCTel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtCEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCCreate)
                    .addComponent(btnCAlterar)
                    .addComponent(btnCDeletar))
                .addGap(261, 261, 261))
        );

        setBounds(0, 0, 640, 477);
    }// </editor-fold>//GEN-END:initComponents

    private void txtCEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCEmailActionPerformed

    private void txtCNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCNomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCNomeActionPerformed

    private void txtCpesqActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCpesqActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCpesqActionPerformed

    private void btnCCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCCreateActionPerformed
        // TODO add your handling code here:

        //chamar método adicionar
        adicionar();
    }//GEN-LAST:event_btnCCreateActionPerformed

    private void txtCpesqKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCpesqKeyReleased
        // o evento abaixo pesquisa em tempo real

        pesquisar_cliente();
    }//GEN-LAST:event_txtCpesqKeyReleased

    private void tblclMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblclMouseClicked
        //chamando o método setar campos

        setar_campos();
    }//GEN-LAST:event_tblclMouseClicked

    private void btnCAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCAlterarActionPerformed
        // Chamar o método alterar

        alterar();
    }//GEN-LAST:event_btnCAlterarActionPerformed

    private void txtIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdActionPerformed

    private void btnCDeletarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCDeletarActionPerformed
        // chamar método remover
        
        remover();
    }//GEN-LAST:event_btnCDeletarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCAlterar;
    private javax.swing.JButton btnCCreate;
    private javax.swing.JButton btnCDeletar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblcl;
    private javax.swing.JTextField txtCEmail;
    private javax.swing.JTextField txtCEnd;
    private javax.swing.JTextField txtCNome;
    private javax.swing.JTextField txtCTel;
    private javax.swing.JTextField txtCpesq;
    private javax.swing.JTextField txtId;
    // End of variables declaration//GEN-END:variables
}
