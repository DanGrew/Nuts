/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.recipe.creator.composite;

import uk.dangrew.kode.javafx.table.base.ConceptTable;
import uk.dangrew.kode.javafx.table.column.ConceptTableColumnsPopulator;
import uk.dangrew.nuts.graphics.table.TableComponents;
import uk.dangrew.nuts.graphics.table.TableConfiguration;
import uk.dangrew.nuts.graphics.table.configuration.TableColumnWidths;
import uk.dangrew.nuts.graphics.table.configuration.TableViewColumnConfigurer;
import uk.dangrew.nuts.recipe.constraint.tightbound.TightBoundConstraint;

public class TightBoundsConstraintColumns< SubjectT > implements ConceptTableColumnsPopulator< TightBoundConstraint< SubjectT > > {

   private static final String COLUMN_TITLE_UNIT = "Subject";
   private static final String COLUMN_TITLE_LOWER_BOUND = "Lower Bound";
   private static final String COLUMN_TITLE_UPPER_BOUND = "Upper Bound";
   static final double COLUMN_WIDTH_NAME = 0.3;
   
   private final TableConfiguration configuration;
   private final TableColumnWidths widths;
   private ConceptTable< TightBoundConstraint< SubjectT > > table;

   public TightBoundsConstraintColumns( TableComponents< TightBoundConstraint< SubjectT > > components ) {
      this( 
               new TableColumnWidths()
                  .withFoodNameWidth( 0.3 )
                  .withCombinedUnitWidth( 0.7 ),
               components
      );
   }//End Constructor
   
   private TightBoundsConstraintColumns(
            TableColumnWidths widths, 
            TableComponents< TightBoundConstraint< SubjectT > > components 
   ) {
      this.configuration = new TableConfiguration();
      this.widths = widths;
   }//End Constructor
   
   @Override public final void populateColumns( ConceptTable< TightBoundConstraint< SubjectT > > table ) {
      this.table = table;
      this.table.getColumns().clear();
      
      configuration.initialiseStringColumn( 
               new TableViewColumnConfigurer<>( table ), 
               COLUMN_TITLE_UNIT, 
               0.3, 
               c -> c.description().get() 
      );
      configuration.initialiseDoubleColumn( 
               new TableViewColumnConfigurer<>( table ), 
               COLUMN_TITLE_LOWER_BOUND, 
               0.3, 
               TightBoundConstraint::lowerBound, 
               true 
      );
      configuration.initialiseDoubleColumn( 
               new TableViewColumnConfigurer<>( table ), 
               COLUMN_TITLE_UPPER_BOUND, 
               0.3, 
               TightBoundConstraint::upperBound, 
               true 
      );
   }//End Method
   
}//End Class
