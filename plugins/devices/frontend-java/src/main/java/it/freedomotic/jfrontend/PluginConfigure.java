/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.freedomotic.jfrontend;

import com.google.inject.Inject;
import it.freedomotic.api.Client;
import it.freedomotic.api.Plugin;
import it.freedomotic.plugins.ClientStorage;
import it.freedomotic.plugins.filesystem.PluginLoaderFilesystem;
import it.freedomotic.util.I18n;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author enrico
 */
public class PluginConfigure
        extends javax.swing.JFrame {

    @Inject
    private ClientStorage clients;
    @Inject
    private PluginLoaderFilesystem pluginsLoader;
    private static HashMap<Plugin, String> predefined = new HashMap<Plugin, String>();

    /**
     * Creates new form PluginConfigure
     */
    public PluginConfigure() {
        initComponents();
        populatePluginsList();
        //add listener to category selection changes
        cmbPlugin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Plugin item = (Plugin) cmbPlugin.getSelectedItem();
                btnDefault.setEnabled(false);
                getConfiguration(item);
            }
        });

        try {
            cmbPlugin.setSelectedIndex(0);
        } catch (Exception e) {
        }

        setPreferredSize(new Dimension(650, 550));
        pack();
        setVisible(true);
    }

    private void populatePluginsList() {
        cmbPlugin.removeAllItems();

        for (Client client : clients.getClients("plugin")) {
            if (client instanceof Plugin) {
                Plugin plugin = (Plugin) client;
                cmbPlugin.addItem(plugin);
            }
        }
    }

    private void getConfiguration(Plugin item) {
        txtArea.setContentType("text/xml");

        String config = readConfiguration(item.getFile()).trim();
        //add old config to predefined to be restored in a later step
        predefined.put(item, config);
        btnDefault.setEnabled(true);
        txtArea.setText(config);
    }

    private String readConfiguration(File file) {
        FileInputStream fis;
        BufferedInputStream bis;
        DataInputStream dis;
        StringBuilder buff = new StringBuilder();

        try {
            fis = new FileInputStream(file);

            // Here BufferedInputStream is added for fast reading.
            bis = new BufferedInputStream(fis);
            dis = new DataInputStream(bis);

            // dis.available() returns 0 if the file does not have more lines.
            while (dis.available() != 0) {
                // this statement reads the line from the file and print it to
                // the console.
                buff.append(dis.readLine()).append("\n");
            }

            // dispose all the resources after using them.
            fis.close();
            bis.close();
            dis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return buff.toString();
    }

    private void saveConfiguration(File file, String text)
            throws IOException {
        // Create file 
        FileWriter fstream = new FileWriter(file);
        BufferedWriter out = new BufferedWriter(fstream);
        out.write(text);
        //Close the output stream
        out.close();
    }

    private void rollbackConfiguration() {
        Plugin item = (Plugin) cmbPlugin.getSelectedItem();
        txtArea.setText(predefined.get(item));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings( "unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents(  )
    {
        cmbPlugin = new javax.swing.JComboBox(  );
        btnSave = new javax.swing.JButton(  );
        btnCancel = new javax.swing.JButton(  );
        jScrollPane2 = new javax.swing.JScrollPane(  );
        txtArea = new javax.swing.JEditorPane(  );
        btnDefault = new javax.swing.JButton(  );

        setDefaultCloseOperation( javax.swing.WindowConstants.DISPOSE_ON_CLOSE );
        setTitle( I18n.msg( this, "plugins_configuration_editor" ) );

        btnSave.setText( I18n.msg( "save" ) );
        btnSave.addActionListener( new java.awt.event.ActionListener(  )
            {
                public void actionPerformed( java.awt.event.ActionEvent evt )
                {
                    btnSaveActionPerformed( evt );
                }
            } );

        btnCancel.setText( I18n.msg( "cancel" ) );
        btnCancel.addActionListener( new java.awt.event.ActionListener(  )
            {
                public void actionPerformed( java.awt.event.ActionEvent evt )
                {
                    btnCancelActionPerformed( evt );
                }
            } );

        jScrollPane2.setViewportView( txtArea );

        btnDefault.setText( I18n.msg( "restore_default" ) );
        btnDefault.setEnabled( false );
        btnDefault.addActionListener( new java.awt.event.ActionListener(  )
            {
                public void actionPerformed( java.awt.event.ActionEvent evt )
                {
                    btnDefaultActionPerformed( evt );
                }
            } );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout( getContentPane(  ) );
        getContentPane(  ).setLayout( layout );
        layout.setHorizontalGroup( layout.createParallelGroup( javax.swing.GroupLayout.Alignment.LEADING )
                                         .addGroup( layout.createSequentialGroup(  ).addContainerGap(  )
                                                          .addGroup( layout.createParallelGroup( javax.swing.GroupLayout.Alignment.LEADING )
                                                                           .addComponent( cmbPlugin,
                                                                                          javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                          0,
                                                                                          javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                          Short.MAX_VALUE )
                                                                           .addComponent( jScrollPane2 )
                                                                           .addGroup( layout.createSequentialGroup(  )
                                                                                            .addComponent( btnSave )
                                                                                            .addPreferredGap( javax.swing.LayoutStyle.ComponentPlacement.RELATED )
                                                                                            .addComponent( btnCancel )
                                                                                            .addPreferredGap( javax.swing.LayoutStyle.ComponentPlacement.RELATED )
                                                                                            .addComponent( btnDefault )
                                                                                            .addGap( 0, 0,
                                                                                                     Short.MAX_VALUE ) ) )
                                                          .addContainerGap(  ) ) );
        layout.setVerticalGroup( layout.createParallelGroup( javax.swing.GroupLayout.Alignment.LEADING )
                                       .addGroup( layout.createSequentialGroup(  ).addContainerGap(  )
                                                        .addComponent( cmbPlugin,
                                                                       javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                       javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                       javax.swing.GroupLayout.PREFERRED_SIZE )
                                                        .addPreferredGap( javax.swing.LayoutStyle.ComponentPlacement.RELATED )
                                                        .addComponent( jScrollPane2,
                                                                       javax.swing.GroupLayout.PREFERRED_SIZE, 390,
                                                                       javax.swing.GroupLayout.PREFERRED_SIZE )
                                                        .addGap( 18, 18, 18 )
                                                        .addGroup( layout.createParallelGroup( javax.swing.GroupLayout.Alignment.BASELINE )
                                                                         .addComponent( btnSave ).addComponent( btnCancel )
                                                                         .addComponent( btnDefault ) ).addContainerGap(  ) ) );

        pack(  );
    } // </editor-fold>//GEN-END:initComponents

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt)    {//GEN-FIRST:event_btnCancelActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt)    {//GEN-FIRST:event_btnSaveActionPerformed

        Plugin item = (Plugin) cmbPlugin.getSelectedItem();
        String name = item.getName();

        try {
            saveConfiguration(item.getFile(),
                    txtArea.getText());
            //stopping and unloading the plugin
            item.stop();
            clients.remove(item);
            //reload it with the new configuration
            System.out.println(item.getFile().getParentFile().toString());
            pluginsLoader.loadPlugin(item.getFile().getParentFile());

            //if not loaded sucessfully reset to old configuration
            if (clients.get(name) == null) {
                //reset to old working config and reload plugin
                rollbackConfiguration();
                saveConfiguration(item.getFile(),
                        txtArea.getText());
                pluginsLoader.loadPlugin(item.getFile().getParentFile());
                clients.get(name).start();
                JOptionPane.showMessageDialog(this,
                        I18n.msg(this,"warn_reset_old_config"));
            } else {
                this.dispose();
            }
        } catch (Exception ex) {
            Logger.getLogger(PluginConfigure.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnDefaultActionPerformed(java.awt.event.ActionEvent evt)    {//GEN-FIRST:event_btnDefaultActionPerformed
        rollbackConfiguration();
    }//GEN-LAST:event_btnDefaultActionPerformed
      // Variables declaration - do not modify//GEN-BEGIN:variables

    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDefault;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox cmbPlugin;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JEditorPane txtArea;

    // End of variables declaration//GEN-END:variables
}
