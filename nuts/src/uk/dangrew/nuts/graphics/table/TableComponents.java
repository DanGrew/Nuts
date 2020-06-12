package uk.dangrew.nuts.graphics.table;

import java.util.function.Function;

import uk.dangrew.kode.javafx.table.base.ConceptTable;
import uk.dangrew.kode.javafx.table.column.ConceptTableColumnsPopulator;
import uk.dangrew.kode.javafx.table.controller.ConceptTableViewController;
import uk.dangrew.kode.javafx.table.controls.ConceptTableWithControls;
import uk.dangrew.nuts.configuration.NutsSettings;
import uk.dangrew.nuts.graphics.common.CheckBoxController;
import uk.dangrew.nuts.graphics.selection.model.SimpleFoodModel;
import uk.dangrew.kode.javafx.table.controls.TableControls;
import uk.dangrew.nuts.store.Database;

public class TableComponents< TypeT >{

   private Database database;
   
   private ConceptTableColumnsPopulator< TypeT > columnsPopulator; 
   private ConceptTableViewController< TypeT > controller;
   private CheckBoxController< TypeT > checkBoxController;
   
   private TableControls tableControls;
   private SimpleFoodModel foodModel;
   
   public NutsSettings settings(){
      return database.settings();
   }//End Method
   
   public Database database(){
      return database;
   }//End Method
   
   public TableComponents< TypeT > withDatabase( Database database ) {
      this.database = database;
      return this;
   }//End Method
   
   public TableComponents< TypeT > applyColumns( Function< TableComponents< TypeT >, ConceptTableColumnsPopulator< TypeT > > columns ) {
      this.columnsPopulator = columns.apply( this );
      return this;
   }//End Method
   
   public TableComponents< TypeT > withColumns( ConceptTableColumnsPopulator< TypeT > columns ) {
      this.columnsPopulator = columns;
      return this;
   }//End Method
   
   public TableComponents< TypeT > withController( ConceptTableViewController< TypeT > controller ) {
      this.controller = controller;
      return this;
   }//End Method
   
   public CheckBoxController< TypeT > checkBoxController(){
      return checkBoxController;
   }//End Method
   
   public TableComponents< TypeT > withCheckBoxController( CheckBoxController< TypeT > controller ) {
      this.checkBoxController = controller;
      return this;
   }//End Method
   
   public SimpleFoodModel foodModel(){
      return foodModel;
   }//End Method
   
   public TableComponents< TypeT > withFoodModel( SimpleFoodModel model ) {
      this.foodModel = model;
      return this;
   }//End Method
   
   public TableControls tableControls(){
      return tableControls;
   }//End Method
   
   public TableComponents< TypeT > withControls( TableControls tableControls ) {
      this.tableControls = tableControls;
      return this;
   }//End Method
   
   public ConceptTable< TypeT > buildTable(){
      return new ConceptTable<>( columnsPopulator, controller );
   }//End Method
   
   public ConceptTableWithControls< TypeT > buildTableWithControls(String title ){
      return new ConceptTableWithControls<>( title, buildTable(), tableControls() );
   }//End Method
   
}//End Class
