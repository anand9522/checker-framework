// ***** This file is automatically generated from SequenceScalar.java.jpp

package daikon.inv.binary.sequenceScalar;

import daikon.*;
import daikon.inv.*;
import daikon.inv.binary.BinaryInvariant;

import utilMDE.*;

/**
 * Abstract base class for comparing double sequences to double
 * variables.  Note that the sequence must always be passed in first.
 **/

public abstract class SequenceFloat extends BinaryInvariant {

  // We are Serializable, so we specify a version to allow changes to
  // method signatures without breaking serialization.  If you add or
  // remove fields, you should change this number to the current date.
  static final long serialVersionUID = 20040113L;

  protected SequenceFloat (PptSlice ppt) {
    super(ppt);
  }

  /**
   * Returns whether or not the specified types are valid.  All
   * subclasses accept a scalar in one parameter and an array over
   * the same scalar type in the other
   */
  public boolean valid_types (VarInfo[] vis) {

    if (vis.length != 2)
      return (false);

    if (!vis[0].file_rep_type.baseIsFloat()
        || !vis[1].file_rep_type.baseIsFloat())
      return (false);

    return ((vis[0].file_rep_type.isArray() && !vis[1].file_rep_type.isArray())
          || !vis[0].file_rep_type.isArray() && vis[1].file_rep_type.isArray());
  }

  /**
   * Since the order is determined from the vars and the sequence is always
   * first, no permute is necesesary.  Subclasses can override if necessary
   */
  protected Invariant resurrect_done_swapped() {
    return this;
  }

  /**
   * Since the order is determined from the vars and the sequence is always
   * first, this is essentially symmetric.  Subclasses can override if necessary
   */
  public boolean is_symmetric() {
    return (true);
  }

   // Check if swap occurred and call one of the other two methods
  protected Invariant resurrect_done(int[] permutation) {
    Assert.assertTrue(permutation.length == 2);
    // Assert.assertTrue(ArraysMDE.fn_is_permutation(permutation));
    if (permutation[0] == 1)
      return resurrect_done_swapped();
    else
      return resurrect_done_unswapped();
  }

   /**
   * Subclasses can override in the rare cases they need to fix things
   * even when not swapped.
   **/
  protected Invariant resurrect_done_unswapped() {
    // do nothing
    return this;
  }

 protected final boolean seq_first() {
    return (ppt.var_infos[0].rep_type == ProglangType.DOUBLE_ARRAY);
  }

  protected final int seq_index() {
    return (seq_first() ? 0 : 1);
  }

  protected final int scl_index() {
    return (seq_first() ? 1 : 0);
  }

  public VarInfo seqvar() {
    return (ppt.var_infos[seq_index()]);
  }

  public VarInfo sclvar() {
    return (ppt.var_infos[scl_index()]);
  }

  /**
   * Return the sequence variable in the tuple whose VarInfos are
   * corresponds to this.ppt.var_infos.
   **/
  public VarInfo seqvar(VarInfo[] vis) {
    return (vis[seq_index()]);
  }

  /**
   * Return the scalar variable in the tuple whose VarInfos are
   * corresponds to this.ppt.var_infos.
   **/
  public VarInfo sclvar(VarInfo[] vis) {
    return vis[scl_index()];
  }

  public InvariantStatus check(Object val1, Object val2, int mod_index, int count) {
    Assert.assertTrue(! falsified);
    Assert.assertTrue((mod_index >= 0) && (mod_index < 4));
    double[] v1 = (double[]) val1;
    double v2 = ((Double) val2).doubleValue();
    if (v1 == null) {
    } else if (mod_index == 0) {
      return check_unmodified(v1, v2, count);
    } else {
      return check_modified(v1, v2, count);
    }
    return InvariantStatus.NO_CHANGE;
  }

  public InvariantStatus add(Object val1, Object val2, int mod_index, int count) {
    Assert.assertTrue(! falsified);
    Assert.assertTrue((mod_index >= 0) && (mod_index < 4));
    double[] v1 = (double[]) val1;
    double v2 = ((Double) val2).doubleValue();
    if (v1 == null) {
    } else if (mod_index == 0) {
      return add_unmodified(v1, v2, count);
    } else {
      return add_modified(v1, v2, count);
    }
    return InvariantStatus.NO_CHANGE;
  }

  public abstract InvariantStatus check_modified(double[] v1, double v2, int count);

  public InvariantStatus check_unmodified(double[] v1, double v2, int count) {
    return InvariantStatus.NO_CHANGE;
  }

  /**
   * This method need not check for falsified;
   * that is done by the caller.
   **/
  public abstract InvariantStatus add_modified(double[] v1, double v2, int count);

  /**
   * By default, do nothing if the value hasn't been seen yet.
   * Subclasses can override this.
   **/
  public InvariantStatus add_unmodified(double[] v1, double v2, int count) {
    return InvariantStatus.NO_CHANGE;
  }

}
