package uk.dangrew.nuts.graphics.research;

import javafx.scene.layout.BorderPane;
import uk.dangrew.kode.textflow.model.TextFlowParser;
import uk.dangrew.nuts.research.ResearchArticle;
import uk.dangrew.nuts.research.ResearchArticleStore;
import uk.dangrew.nuts.resources.research.ResearchLibrary;

public class UiResearchPane extends BorderPane {
   
   private final TextFlowParser parser;
   
   public UiResearchPane( ResearchArticleStore store ) {
      this( new TextFlowParser(), store );
   }//End Constructor
   
   UiResearchPane( TextFlowParser parser, ResearchArticleStore store ) {
      new ResearchLibrary().loadResearch( store );
      this.parser = parser;
      
      UiResearchController controller = new UiResearchController( store );
      controller.associate( this );
      
      setLeft( new UiResearchControls( controller ) );
   }//End Class

   public void show( ResearchArticle article ) {
      setCenter( parser.parse( article.content().get() ).orElse( null ) );
   }//End Method

}//End Class
