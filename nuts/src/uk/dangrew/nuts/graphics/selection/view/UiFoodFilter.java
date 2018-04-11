package uk.dangrew.nuts.graphics.selection.view;

import java.util.Collection;

import uk.dangrew.nuts.graphics.selection.model.FoodFilters;
import uk.dangrew.nuts.graphics.selection.model.FoodSelectionTypes;
import uk.dangrew.nuts.label.Label;

public interface UiFoodFilter {

   public void invertSort( boolean sort );

   public void filterOptions( String filter );

   public void useSelectionType( FoodSelectionTypes type );

   public void applyFilters( Collection< FoodFilters > checkedItems );

   public void applyLabels( Collection< Label > checkedItems );

}//End Interface

