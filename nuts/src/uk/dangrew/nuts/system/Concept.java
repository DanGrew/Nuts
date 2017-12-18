package uk.dangrew.nuts.system;

public interface Concept {

   /**
    * Access to the {@link Properties} associated.
    * @return the {@link Properties}.
    */
   public Properties properties();
   
   /**
    * Method to duplicate the {@link Concept}.
    * @param referenceId the reference to append to the name.
    * @return the duplicated {@link Concept}.
    */
   public Concept duplicate( String referenceId );
   
}//End Interface

