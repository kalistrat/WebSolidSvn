package com.pkgUserTree;

/**
 * Created by kalistrat on 18.01.2018.
 */

import com.staticMethods;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.sql.*;

/**
 * Created by kalistrat on 18.11.2016.
 */
public class tTree extends Tree {

    public HierarchicalContainer TreeContainer;
    private String userLog;


    public tTree(String eUserLog,com.mainView eMainView){

        TreeContainer = new HierarchicalContainer();
        userLog = eUserLog;

        TreeContainer.addContainerProperty(1, Integer.class, null);
        TreeContainer.addContainerProperty(2, Integer.class, null);
        TreeContainer.addContainerProperty(3, Integer.class, null);
        TreeContainer.addContainerProperty(4, String.class, null);
        TreeContainer.addContainerProperty(5, String.class, null);
        TreeContainer.addContainerProperty(6, Integer.class, null);
        TreeContainer.addContainerProperty(7, String.class, null);

        setUserTreeData();
        setItemCaptionPropertyId(4);

        setContainerDataSource(this.TreeContainer);

        //Разворачиваю дерево
        for (Object id : this.rootItemIds()) {
            this.expandItemsRecursively(id);

        }

        for (Object id : this.TreeContainer.getItemIds()){
            if (!this.TreeContainer.hasChildren(id))
                this.TreeContainer.setChildrenAllowed(id, false);
        }


        this.select(1);

        this.addValueChangeListener(new ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                if(valueChangeEvent.getProperty().getValue() != null)
                {
                    Item SelectedItem = TreeContainer.getItem(valueChangeEvent.getProperty().getValue());
                    eMainView.TreeContentUsr.tTreeContentLayoutRefresh((int) SelectedItem.getItemProperty(2).getValue(),(int) SelectedItem.getItemProperty(6).getValue());
                }
            }
        });

    }

    private String getXMLUserTreeData(){
        try {

            Class.forName(staticMethods.JDBC_DRIVER);
            Connection Con = DriverManager.getConnection(
                    staticMethods.DB_URL
                    , staticMethods.USER
                    , staticMethods.PASS
            );

            CallableStatement Stmt = Con.prepareCall("{? = call pkg_user_model_tree.f_get_user_tree(?)}");
            Stmt.registerOutParameter(1, Types.CLOB);
            Stmt.setString(2,userLog);
            Stmt.execute();
            //Clob CondValue = Stmt.getClob(1);
            //System.out.println("getXMLStateData : CondValue : " + CondValue);
            String resultStr = staticMethods.clobToString(Stmt.getClob(1));
            Con.close();
            return resultStr;

        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
            return null;
        }catch(Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
            return null;
        }
    }

    public void setUserTreeData(){

        try {

            Document xmlDocument = staticMethods.loadXMLFromString(getXMLUserTreeData());

            Node userTreeNode = (Node) XPathFactory.newInstance().newXPath()
                    .compile("/USER_TREE_DATA").evaluate(xmlDocument, XPathConstants.NODE);

            NodeList treeLeafDataList = userTreeNode.getChildNodes();

            for (int i = 0; i < treeLeafDataList.getLength(); i++) {
                Item newItem = TreeContainer.addItem(i+1);
                NodeList treeLeafData = treeLeafDataList.item(i).getChildNodes();
                for (int j = 0; j < treeLeafData.getLength(); j++) {
                    if (treeLeafData.item(j).getNodeName().equals("USER_MODEL_TREE_ID")) {
                        newItem.getItemProperty(1).setValue(Integer.parseInt(treeLeafData.item(j).getTextContent()));
                    } else if (treeLeafData.item(j).getNodeName().equals("LEAF_ID")) {
                        newItem.getItemProperty(2).setValue(Integer.parseInt(treeLeafData.item(j).getTextContent()));
                    } else if (treeLeafData.item(j).getNodeName().equals("PARENT_LEAF_ID")) {
                        newItem.getItemProperty(3).setValue(Integer.parseInt(treeLeafData.item(j).getTextContent()));
                    } else if (treeLeafData.item(j).getNodeName().equals("LEAF_NAME")) {
                        newItem.getItemProperty(4).setValue(treeLeafData.item(j).getTextContent());
                    } else if (treeLeafData.item(j).getNodeName().equals("ICON_CODE")) {
                        newItem.getItemProperty(5).setValue(treeLeafData.item(j).getTextContent());
                        newItem.getItemProperty(7).setValue(treeLeafData.item(j).getTextContent());
                    } else if (treeLeafData.item(j).getNodeName().equals("USER_MODEL_ID")) {
                        newItem.getItemProperty(6).setValue(Integer.parseInt(treeLeafData.item(j).getTextContent()));
                    }
                }
            }

            for (int i = 0; i < TreeContainer.size(); i++){

                if (((Integer) TreeContainer.getItem(i+1).getItemProperty(3).getValue()).intValue() != 0) {
                    TreeContainer.setParent(i+1, TreeContainer.getItem(i+1).getItemProperty(3).getValue());
                }

            }

            for (int j=1;j<TreeContainer.size()+1;j++) {
                String IconStr =  (String) TreeContainer.getItem(j).getItemProperty(5).getValue();

                if (IconStr.equals("FOLDER")) {
                    setItemIcon(j, VaadinIcons.FOLDER);
                }
                if (IconStr.equals("PART")) {
                    setItemIcon(j, FontAwesome.CUBE);
                }
                if (IconStr.equals("ASSEMBLY")) {
                    setItemIcon(j, VaadinIcons.CUBES);
                }
                if (IconStr.equals("DOCUMENT")) {
                    setItemIcon(j, VaadinIcons.FILE);
                }
                if (IconStr.equals("COMPLEX")) {
                    setItemIcon(j, VaadinIcons.QUESTION_CIRCLE);
                }

            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }

}