package uk.dangrew.nuts.graphics.table.tree;

import uk.dangrew.kode.javafx.table.base.ConceptTableRow;

public interface TreeTableController<ConceptT> extends ConceptTableRow< ConceptT >{
   
   public void add();

   public void copy();

   public void remove();

   public void moveUp();
   
   public void moveDown();
   
}//End Interface
