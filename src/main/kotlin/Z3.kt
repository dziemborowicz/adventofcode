import com.microsoft.z3.ArithExpr
import com.microsoft.z3.ArithSort
import com.microsoft.z3.BoolExpr
import com.microsoft.z3.BoolSort
import com.microsoft.z3.Context
import com.microsoft.z3.Expr

fun Context.mkAnd(t: Iterable<Expr<BoolSort>>): BoolExpr = mkAnd(*t.toList().toTypedArray())

fun Context.mkOr(t: Iterable<Expr<BoolSort>>): BoolExpr = mkOr(*t.toList().toTypedArray())

fun <R : ArithSort> Context.mkAdd(t: Iterable<Expr<out R>>): ArithExpr<R> =
  mkAdd(*t.toList().toTypedArray())

fun <R : ArithSort> Context.mkMul(t: Iterable<Expr<out R>>): ArithExpr<R> =
  mkMul(*t.toList().toTypedArray())

fun <R : ArithSort> Context.mkSub(t: Iterable<Expr<out R>>): ArithExpr<R> =
  mkSub(*t.toList().toTypedArray())
