package org.pitest.mutationtest.engine.gregor.mutators.Joke;

import java.util.HashMap;
import java.util.Map;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.pitest.mutationtest.engine.gregor.AbstractJumpMutator;
import org.pitest.mutationtest.engine.gregor.MethodInfo;
import org.pitest.mutationtest.engine.gregor.MethodMutatorFactory;
import org.pitest.mutationtest.engine.gregor.MutationContext;

public enum RORMutatorEqual implements MethodMutatorFactory {

  ROR_MUTATOR_TOEQUAL;

  @Override
  public MethodVisitor create(final MutationContext context,
      final MethodInfo methodInfo, final MethodVisitor methodVisitor) {
    return new RORMutatorEqualMethodVisitor(this, context, methodVisitor);
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

class RORMutatorEqualMethodVisitor extends AbstractJumpMutator {

 RORMutatorEqualMethodVisitor(final MethodMutatorFactory factory,final MutationContext context, final MethodVisitor delegateMethodVisitor) {
    super(factory, context, delegateMethodVisitor);
 }
  
 private static final Map<Integer, AbstractJumpMutator.Substitution> MUTATIONS   = new HashMap<Integer, AbstractJumpMutator.Substitution>();

  static {
        MUTATIONS.put(Opcodes.IFLT, new AbstractJumpMutator.Substitution(Opcodes.IFEQ, "ROR - <  to =="));
        MUTATIONS.put(Opcodes.IFLE, new AbstractJumpMutator.Substitution(Opcodes.IFEQ, "ROR - <= to =="));
        MUTATIONS.put(Opcodes.IFGT, new AbstractJumpMutator.Substitution(Opcodes.IFEQ, "ROR - >  to =="));
        MUTATIONS.put(Opcodes.IFGE, new AbstractJumpMutator.Substitution(Opcodes.IFEQ, "ROR - >= to =="));
        MUTATIONS.put(Opcodes.IFNE, new AbstractJumpMutator.Substitution(Opcodes.IFEQ, "ROR - != to =="));
  }

  @Override
  protected Map<Integer, AbstractJumpMutator.Substitution> getMutations() {
    return MUTATIONS;
  }
}