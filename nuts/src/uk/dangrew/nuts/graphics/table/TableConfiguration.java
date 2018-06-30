/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.table;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import uk.dangrew.kode.comparator.Comparators;
import uk.dangrew.kode.javafx.spinner.StringExtractConverter;
import uk.dangrew.kode.javafx.style.Conversions;
import uk.dangrew.kode.javafx.table.TableViewEditCommitHandler;
import uk.dangrew.nuts.configuration.NutsSettings;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.graphics.common.CheckBoxController;
import uk.dangrew.nuts.graphics.table.configuration.TableColumnConfigurer;
import uk.dangrew.nuts.graphics.table.configuration.TableViewColumnConfigurer;
import uk.dangrew.nuts.nutrients.Nutrition;
import uk.dangrew.nuts.nutrients.NutritionalUnit;
import uk.dangrew.nuts.system.Concept;
import uk.dangrew.nuts.system.Properties;

/**
 * {@link TableConfiguration} provides common configuration for JavaFx tables using {@link TableColumnConfigurer}s.
 */
public class TableConfiguration {
   
   private final static Conversions conversions = new Conversions();
   
   /**
    * Method to initialise a {@link TableColumn} with the given properties/behaviour for a {@link String} value.
    * @param table the {@link TableView} to configure.
    * @param title the title of the {@link TableColumn}.
    * @param widthProportion the proportion of the width the {@link TableColumn} should be.
    * @param propertyRetriever the {@link Function} to retrieve the value from the {@link FoodProperties}.
    * @param editable whether the column should be editable.
    */
   public < FoodTypeT extends Food > void initialiseFoodPropertyStringColumn( 
            TableView< ConceptTableRow< FoodTypeT > > table,
            String title, 
            double widthProportion,
            Function< Properties, ObjectProperty< String > > propertyRetriever, 
            boolean editable
   ){
      initialiseStringColumn( 
               table, title, widthProportion, 
               f -> propertyRetriever.apply( f.properties() ), 
               editable 
      );
   }//End Method
   
   /**
    * Method to initialise a {@link TableColumn} with the given properties/behaviour for a {@link String} value.
    * @param table the {@link TableView} to configure.
    * @param title the title of the {@link TableColumn}.
    * @param widthProportion the proportion of the width the {@link TableColumn} should be.
    * @param propertyRetriever the {@link Function} to retrieve the value from the {@link FoodProperties}.
    * @param editable whether the column should be editable.
    */
   public < TypeT extends Concept > void initialiseStringColumn( 
            TableView< ConceptTableRow< TypeT > > table,
            String title, 
            double widthProportion,
            Function< TypeT, ObjectProperty< String > > propertyRetriever, 
            boolean editable
   ){
      initialiseStringColumn( 
               new TableViewColumnConfigurer<>( table ), 
               title, 
               widthProportion, 
               propertyRetriever, 
               editable
      );
   }//End Method
   
   public < TypeT extends Concept > void initialiseStringColumn(
            TableColumnConfigurer< TypeT, String > configurer,
            String title, 
            double widthProportion,
            Function< TypeT, ObjectProperty< String > > propertyRetriever, 
            boolean editable
   ){
      configurer.setTitle( title );
      configurer.bindPrefWidth( widthProportion );
      configurer.setCellValueFactory( propertyRetriever );
      configurer.setTextFieldCellFactory();
      configurer.setOnEditCommit( ( r, v ) -> 
         propertyRetriever.apply( r ).set( v ) 
      );
      configurer.setEditable( editable );
   }//End Method
   
   /**
    * Method to initialise a {@link TableColumn} with the given properties/behaviour for a {@link String} value.
    * @param table the {@link TableView} to configure.
    * @param title the title of the {@link TableColumn}.
    * @param width the proportion of the width the {@link TableColumn} should be.
    * @param propertyRetriever the {@link Function} to retrieve the value from the {@link FoodProperties}.
    */
   public < ConceptTypeT > void initialiseStringColumn(
            TableView< ConceptTableRow< ConceptTypeT > > table,
            String title,
            double width,
            Function< ConceptTypeT, String > propertyRetriever
   ){
      initialiseStringColumn( 
               new TableViewColumnConfigurer<>( table ), 
               title, 
               width, 
               propertyRetriever 
      );
   }//End Method
   
   @Deprecated public < RowTypeT > void initialiseCustomStringColumn(
            TableView< RowTypeT > table,
            String title,
            double width,
            Function< RowTypeT, String > propertyetriever
   ){
      TableColumn< RowTypeT, String > date = new TableColumn<>( title );
      date.prefWidthProperty().bind( table.widthProperty().multiply( width ) );
      date.setCellValueFactory( object -> new SimpleObjectProperty<>( propertyetriever.apply( object.getValue() ) ) );
      date.setEditable( false );
      table.getColumns().add( date );
   }//End Method
   
   @Deprecated public < RowTypeT > void initialiseCustomDoubleColumn(
            TableView< RowTypeT > table,
            String title, 
            double widthProportion,
            Function< RowTypeT, ObjectProperty< Double > > propertyRetriever,
            boolean editable
   ){
      TableColumn< RowTypeT, String > column = new TableColumn<>( title );
      column.prefWidthProperty().bind( table.widthProperty().multiply( widthProportion ) );
      column.setCellValueFactory( object -> propertyRetriever.apply( object.getValue() ).asString() );
      column.comparatorProperty().set( Comparators.STRING_AS_NUMBER_ASCENDING );
      
      if ( editable ) {
         column.setCellFactory(TextFieldTableCell.forTableColumn());
         column.setOnEditCommit( new TableViewEditCommitHandler<>( ( r, v ) -> 
                  propertyRetriever.apply( r ).set( conversions.nullableStringToDoubleFunction().apply( v ) )
         ) );
      }
      table.getColumns().add( column );
   }//End Method
   
   /**
    * Method to initialise a {@link TableColumn} with the given properties/behaviour for a {@link String} value.
    * @param table the {@link TableView} to configure.
    * @param title the title of the {@link TableColumn}.
    * @param width the proportion of the width the {@link TableColumn} should be.
    * @param propertyRetriever the {@link Function} to retrieve the value from the {@link FoodProperties}.
    */
   public < ConceptTypeT > void initialiseStringColumn(
            TableColumnConfigurer< ConceptTypeT, String > configurer,
            String title,
            double width,
            Function< ConceptTypeT, String > propertyRetriever
   ){
      configurer.setTitle( title );
      configurer.bindPrefWidth( width );
      configurer.setFixedCellValueFactory( propertyRetriever );
      configurer.setEditable( false );
   }//End Method
   
   /**
    * Method to initialise a {@link TableColumn} with the given properties/behaviour for a {@link ObjectProperty}.
    * @param table the {@link TableView} to configure.
    * @param title the title of the {@link TableColumn}.
    * @param widthProportion the proportion of the width the {@link TableColumn} should be.
    * @param propertyRetriever the {@link Function} to retrieve the value from the {@link FoodProperties}.
    * @param editable whether the column is editable.
    */
   @Deprecated public < FoodTypeT extends Food > void initialiseNutrientColumn(
            TableView< ConceptTableRow< FoodTypeT > > table,
            String title, 
            double widthProportion,
            Function< FoodTypeT, ObjectProperty< Double > > propertyRetriever,
            boolean editable
   ){
      initialiseDoubleColumn( 
               table, 
               title, 
               widthProportion, 
               propertyRetriever, 
               editable 
      );
   }//End Method
   
   /**
    * Method to initialise a {@link TableColumn} with the given properties/behaviour for a {@link ObjectProperty}.
    * @param table the {@link TableView} to configure.
    * @param title the title of the {@link TableColumn}.
    * @param widthProportion the proportion of the width the {@link TableColumn} should be.
    * @param propertyRetriever the {@link Function} to retrieve the value from the row.
    * @param editable whether the column is editable.
    */
   public < ConceptTypeT > void initialiseDoubleColumn(
            TableView< ConceptTableRow< ConceptTypeT > > table,
            String title, 
            double widthProportion,
            Function< ConceptTypeT, ObjectProperty< Double > > propertyRetriever,
            boolean editable
   ){
      initialiseDoubleColumn( 
               new TableViewColumnConfigurer<>( table ), 
               title, 
               widthProportion, 
               propertyRetriever, 
               editable 
      );
   }//End Method
   
   public < ConceptTypeT > void initialiseDoubleColumn(
            TableColumnConfigurer< ConceptTypeT, String > configurer,
            String title, 
            double widthProportion,
            Function< ConceptTypeT, ObjectProperty< Double > > propertyRetriever,
            boolean editable
   ){
      configurer.setTitle( title );
      configurer.bindPrefWidth( widthProportion );
      configurer.setCellValueFactoryAsString( propertyRetriever );
      configurer.setComparator( Comparators.STRING_AS_NUMBER_ASCENDING );
      
      if ( editable ) {
         configurer.setTextFieldCellFactory();
         configurer.setOnEditCommit( ( r, v ) -> 
                  propertyRetriever.apply( r ).set( conversions.nullableStringToDoubleFunction().apply( v ) )
         );
      }
   }//End Method
   
   /**
    * Method to initialise a {@link TableColumn} with the given properties/behaviour for a ratio of a {@link NutrientValue}.
    * @param table the {@link TableView} to configure.
    * @param title the title of the {@link TableColumn}.
    * @param widthProportion the proportion of the width the {@link TableColumn} should be.
    * @param propertyRetriever the {@link Function} to retrieve the value from the {@link Food}.
    */
   public < FoodTypeT extends Food > void initialiseRatioColumn(
            TableView< ConceptTableRow< FoodTypeT > > table,
            String title, 
            double widthProportion,
            Function< FoodTypeT, ReadOnlyObjectProperty< Double > > propertyRetriever 
   ){
      initialiseRatioColumn( 
               new TableViewColumnConfigurer<>( table ), 
               title, 
               widthProportion, 
               propertyRetriever 
      );
   }//End Method
   
   public < FoodTypeT extends Food > void initialiseRatioColumn(
            TableColumnConfigurer< FoodTypeT, String > configurer,
            String title, 
            double widthProportion,
            Function< FoodTypeT, ReadOnlyObjectProperty< Double > > propertyRetriever 
   ){
      configurer.setTitle( title );
      configurer.bindPrefWidth( widthProportion );
      configurer.setCellValueFactoryAsString( propertyRetriever );
      configurer.setComparator( Comparators.STRING_AS_NUMBER_ASCENDING );
   }//End Method
   
   /**
    * Method to initialise a {@link TableColumn} with a drop down {@link javafx.scene.control.ComboBox} for the {@link Food}.
    * @param table the {@link TableView} to configure.
    * @param title the title of the {@link TableColumn}.
    * @param widthProportion the proportion of the width the {@link TableColumn} should be.
    * @param propertyRetriever the {@link Function} to retrieve the food from the row.
    * @param propertySetter the {@link BiConsumer} to set the {@link Food} when selected.
    * @param foodOptions the {@link FoodOptions} for the {@link javafx.scene.control.ComboBox}.
    */
   public < ConceptTypeT, FoodTypeT extends Food > void initialiseFoodDropDownColumn(
            TableView< ConceptTableRow< ConceptTypeT > > table,
            String title, 
            double widthProportion,
            Function< ConceptTypeT, ObservableValue< FoodTypeT > > propertyRetriever,
            BiConsumer< ConceptTypeT, FoodTypeT > propertySetter,
            ConceptOptions< FoodTypeT > foodOptions
   ){
      initialiseFoodDropDownColumn( 
               new TableViewColumnConfigurer<>( table ), 
               title, 
               widthProportion, 
               propertyRetriever, 
               propertySetter, 
               foodOptions 
      );
   }//End Method
   
   public < ConceptTypeT, FoodTypeT extends Food > void initialiseFoodDropDownColumn(
            TableColumnConfigurer< ConceptTypeT, FoodTypeT > configurer,
            String title, 
            double widthProportion,
            Function< ConceptTypeT, ObservableValue< FoodTypeT > > propertyRetriever,
            BiConsumer< ConceptTypeT, FoodTypeT > propertySetter,
            ConceptOptions< FoodTypeT > foodOptions
   ){
      configurer.setTitle( title );
      configurer.setComboBoxCellFactory( 
               new StringExtractConverter<>( 
                  object -> object.properties().nameProperty().get(),
                  foodOptions::find,
                  "None"
               ), foodOptions.options() 
      );
      configurer.setCellValueFactory( propertyRetriever );
      configurer.bindPrefWidth( widthProportion );
      configurer.setOnEditCommit( propertySetter::accept );
   }//End Method
   
   /**
    * Method to initialise a {@link TableColumn} with a {@link FoodPortion#portion()} configuration.
    * @param table the {@link TableView} to configure.
    * @param title the title of the {@link TableColumn}.
    * @param widthProportion the proportion of the width the {@link TableColumn} should be.
    */
   public void initialisePortionColumn(
            ConceptTable< FoodPortion > table,
            String title, 
            double widthProportion
   ){
      initialisePortionColumn( 
               new TableViewColumnConfigurer<>( table ), 
               title, 
               widthProportion 
      );
   }//End Method
   
   public void initialisePortionColumn(
            TableColumnConfigurer< FoodPortion, String > configurer,
            String title, 
            double widthProportion
   ){
      configurer.setTitle( title );
      configurer.bindPrefWidth( widthProportion );
      configurer.setCellValueFactoryAsString( FoodPortion::portion );
      configurer.setEditable( true );
      configurer.setTextFieldCellFactory();
      configurer.setOnEditCommit( ( portion, v ) -> portion.setPortion( Double.valueOf( v ) ) );
   }//End Method
   
   public < TypeT extends Concept > void configureCheckBoxController(
            ConceptTable< TypeT > table,
            CheckBoxController< TypeT > controller,
            double widthProportion
   ) {
      configureCheckBoxController( 
               new TableViewColumnConfigurer<>( table ),
               controller, 
               widthProportion 
      );
   }//End Method
   
   public < ConceptTypeT extends Concept > void configureCheckBoxController(
            TableColumnConfigurer< ConceptTypeT, Boolean > configurer,
            CheckBoxController< ConceptTypeT > controller,
            double widthProportion
   ) {
      configurer.bindPrefWidth( widthProportion );
      configurer.setCellValueFactory( controller::propertyFor );
      configurer.setCheckBoxCellFactory();
      configurer.setEditable( true );
   }//End Method

   public <FoodTypeT extends Food > void configureVisibleNutrientUnitColumns( 
            Supplier< TableColumnConfigurer< FoodTypeT, String > > configurerSupplier, 
            Function< FoodTypeT, Nutrition > source, 
            Function< NutritionalUnit, String > unitNaming,
            double availableWidth,
            boolean editableUnits,
            NutsSettings settings
   ) {
      List< NutritionalUnit > showingUnits = Stream.of( NutritionalUnit.values() )
               .filter( u -> settings.showingPropertyFor( u ).get() )
               .collect( Collectors.toList() );
      
      double width = availableWidth / showingUnits.size();
      for ( NutritionalUnit unit : showingUnits ) {
         initialiseDoubleColumn( 
                  configurerSupplier.get(), 
                  unitNaming.apply( unit ), 
                  width, 
                  f -> source.apply( f ).of( unit ), 
                  editableUnits 
         );
      }
   }//End Method
   
}//End Class
