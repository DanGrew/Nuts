package uk.dangrew.nuts.persistence.fooditems;

import uk.dangrew.jupa.json.parse.JsonParser;
import uk.dangrew.jupa.json.structure.JsonStructure;

public interface ConceptPersistence {

   /**
    * Access to the {@link JsonStructure}.
    * @return the {@link JsonStructure}.
    */
   public JsonStructure structure();
   
   /**
    * Access to the {@link JsonParser} reader.
    * @return the {@link JsonParser}.
    */
   public JsonParser readHandles();
   
   /**
    * Access to the {@link JsonParser} writer.
    * @return the {@link JsonParser}.
    */
   public JsonParser writeHandles(); 
   
}//End Interface
