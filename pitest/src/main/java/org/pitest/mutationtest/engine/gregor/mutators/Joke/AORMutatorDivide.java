package org.pitest.mutationtest.engine.gregor.mutators.Joke;

import java.util.HashMap;
import java.util.Map;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.pitest.mutationtest.engine.gregor.AbstractInsnMutator;
import org.pitest.mutationtest.engine.gregor.InsnSubstitution;
import org.pitest.mutationtest.engine.gregor.MethodInfo;
import org.pitest.mutationtest.engine.gregor.MethodMutatorFactory;
import org.pitest.mutationtest.engine.gregor.MutationContext;
import org.pitest.mutationtest.engine.gregor.ZeroOperandMutation;

public enum AORMutatorDivide implements MethodMutatorFactory {

  AOR_MUTATOR_DIVIDE;

  @Override
  public MethodVisitor create(final MutationContext context,final MethodInfo methodInfo, final MethodVisitor methodVisitor) {
    return new AORMutatorDivideMethodVisitor(this, methodInfo, context, methodVisitor);
  }

  @Override
  public String getGloballyUniqueId() {
    return this.getClass().getName();
  }

  @Override
  public String getName() {
    return name();
  }
}

class AORMutatorDivideMethodVisitor extends AbstractInsnMutator {

  AORMutatorDivideMethodVisitor(final MethodMutatorFactory factory,final MethodInfo methodInfo, final MutationContext context,final MethodVisitor writer) {
    super(factory, methodInfo, context, writer);
  }

  private static final Map<Integer, ZeroOperandMutation> MUTATIONS = new HashMap<Integer, ZeroOperandMutation>();
  
  static {
    MUTATIONS.put(Opcodes.ISUB, new InsnSubstitution(Opcodes.IDIV, "AOR - - to /"));
    MUTATIONS.put(Opcodes.IMUL, new InsnSubstitution(Opcodes.IDIV, "AOR - * to /"));
    MUTATIONS.put(Opcodes.IADD, new InsnSubstitution(Opcodes.IDIV, "AOR - + to /"));
  }

  @Override
  protected Map<Integer, ZeroOperandMutation> getMutations() {
   
      return MUTATIONS;
  }
}
