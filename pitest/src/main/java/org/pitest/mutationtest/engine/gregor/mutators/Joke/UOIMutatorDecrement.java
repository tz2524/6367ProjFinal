package org.pitest.mutationtest.engine.gregor.mutators.Joke;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.pitest.mutationtest.engine.MutationIdentifier;
import org.pitest.mutationtest.engine.gregor.MethodInfo;
import org.pitest.mutationtest.engine.gregor.MethodMutatorFactory;
import org.pitest.mutationtest.engine.gregor.MutationContext;

public enum UOIMutatorDecrement implements MethodMutatorFactory {

    DECREMENT;

    @Override
    public MethodVisitor create(final MutationContext context,
            final MethodInfo methodInfo, final MethodVisitor methodVisitor) {

        return new UOIAddDecrementMutatorMethodVisitor(this, context, methodVisitor);
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


// This operator works exclusively on function local variables, not object variables
class UOIAddDecrementMutatorMethodVisitor extends MethodVisitor {

    private final MethodMutatorFactory factory;
    private final MutationContext context;

    UOIAddDecrementMutatorMethodVisitor(final MethodMutatorFactory factory,
            final MutationContext context, final MethodVisitor delegateMethodVisitor) {
        super(Opcodes.ASM6, delegateMethodVisitor);
        this.factory = factory;
        this.context = context;
    }

    @Override
    public void visitIincInsn(final int var, final int increment) {

        final MutationIdentifier newId = this.context.registerMutation(this.factory, "UOI Mutator: Added unary decrement "
                + increment + " -> " + (increment - 1) + " to local variable");

        if (this.context.shouldMutate(newId)) {
            this.mv.visitIincInsn(var, increment - 1);
        } else {
            this.mv.visitIincInsn(var, increment);
        }
    }

}

