module columbiare {
    requires transitive javafx.controls;
    requires transitive java.desktop;

    opens com.glowingpigeon.columbiare to javafx, javafx.controls;
    
    exports com.glowingpigeon.columbiare;
    exports com.glowingpigeon.columbiare.audio;
    exports com.glowingpigeon.columbiare.data;
    exports com.glowingpigeon.columbiare.graphics;
    exports com.glowingpigeon.columbiare.state;
    exports com.glowingpigeon.columbiare.state.premade;
    exports com.glowingpigeon.columbiare.ui;
    exports com.glowingpigeon.columbiare.world;
    exports com.glowingpigeon.columbiare.world.entity;
    exports com.glowingpigeon.columbiare.world.entity.dialogue;
}