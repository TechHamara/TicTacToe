# Add any ProGuard configurations specific to this
# extension here.

-keep public class io.horizon.tictactoe.TicTacToe {
    public *;
 }
-keeppackagenames gnu.kawa**, gnu.expr**

-optimizationpasses 4
-allowaccessmodification
-mergeinterfacesaggressively

-repackageclasses 'io/horizon/tictactoe/repack'
-flattenpackagehierarchy
-dontpreverify
