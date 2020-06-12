package uk.dangrew.nuts.graphics.recipe.creator.composite;

import javafx.scene.layout.BorderPane;
import uk.dangrew.kode.javafx.table.base.ConceptTable;
import uk.dangrew.nuts.graphics.recipe.creator.UiRecipeConstraintController;
import uk.dangrew.nuts.graphics.table.TableComponents;
import uk.dangrew.nuts.recipe.constraint.tightbound.TightBoundConstraint;
import uk.dangrew.nuts.recipe.constraint.tightbound.TightBoundConstraints;

public class UiTightBoundConstraintsConfigurer< SubjectT > extends BorderPane {

   private final ConceptTable< TightBoundConstraint< SubjectT > > table;
   
   public UiTightBoundConstraintsConfigurer( 
            TightBoundConstraints< SubjectT > constraint, 
            UiRecipeConstraintController constraintController
   ) {
      UiTightBoundConstraintsController< SubjectT > controller = new UiTightBoundConstraintsController<>( constraintController );
      setCenter( table = new TableComponents< TightBoundConstraint< SubjectT > >()
                     .applyColumns( TightBoundsConstraintColumns< SubjectT >::new )
                     .withController( controller )
                     .buildTable() 
      );
      controller.select( constraint );
   }//End Constructor

}//End Class
