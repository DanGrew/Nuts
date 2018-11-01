package uk.dangrew.nuts.persistence.resolution;

import uk.dangrew.kode.concept.ConceptStore;
import uk.dangrew.nuts.goal.Goal;
import uk.dangrew.nuts.store.Database;
import uk.dangrew.nuts.template.Template;

public class TemplateGoalResolution implements ResolutionStrategy {

   private final ConceptStore< ? extends Template > store;
   private final String subjectId;
   private final String goalId;
   
   public TemplateGoalResolution( 
            ConceptStore< ? extends Template > store, 
            String subjectId, 
            String goalId 
   ) {
      this.store = store;
      this.subjectId = subjectId;
      this.goalId = goalId;
   }//End Constructor
   
   @Override public void resolve( Database database ) {
      if ( goalId == null || goalId.trim().length() == 0 ) {
         return;
      }
      
      Template subject = store.get( subjectId );
      if ( subject == null ) {
         return;
      }
      
      Goal goal = database.calorieGoals().get( goalId );
      if ( goal == null ) {
         goal = database.proportionGoals().get( goalId );
      }
      if ( goal == null ) {
         System.out.println( "Unable to resolve goal: " + goalId );
         return;
      }
      
      subject.goalAnalytics().goal().set( goal );
   }//End Method

}//End Class
