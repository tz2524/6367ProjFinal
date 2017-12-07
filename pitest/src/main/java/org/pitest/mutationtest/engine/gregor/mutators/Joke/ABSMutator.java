package org.pitest.mutationtest.engine.gregor.mutators.Joke;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.pitest.mutationtest.engine.MutationIdentifier;
import org.pitest.mutationtest.engine.gregor.MethodInfo;
import org.pitest.mutationtest.engine.gregor.MethodMutatorFactory;
import org.pitest.mutationtest.engine.gregor.MutationContext;

public enum ABSMutator implements MethodMutatorFactory {

    ABS_MUTATOR;

    @Override
    public MethodVisitor create(final MutationContext context,
            final MethodInfo methodInfo, final MethodVisitor methodVisitor) {
        return new ABSMutatorMethodVisitor(this, context, methodVisitor);
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

class ABSMutatorMethodVisitor extends MethodVisitor {

    private final MethodMutatorFactory factory;
    private final MutationContext context;

    ABSMutatorMethodVisitor(final MethodMutatorFactory factory,
            final MutationContext context, final MethodVisitor delegateMethodVisitor) {
        super(Opcodes.ASM6, delegateMethodVisitor);
        this.factory = factory;
        this.context = context;
    }

    @Override
    public void visitLdcInsn(Object cst) {
        
final MutationIdentifier id = this.context.registerMutation(factory,"CRCR - Replace with 1");
        
        if (this.context.shouldMutate(id)) {
            if (cst instanceof Integer) {
                //NOT SURE IF THIS WORKS, OR SHOULD I USE visitInsn(aload)?
                super.visitLdcInsn((Integer)cst * -2);
                super.visitInsn(Opcodes.IADD);
            } else if (cst instanceof Double) {
                super.visitLdcInsn((Double)cst * -2);
                super.visitInsn(Opcodes.DADD);
            } else if (cst instanceof Float) {
                super.visitLdcInsn((Float)cst * -2);
                super.visitInsn(Opcodes.FADD);
            } else if (cst instanceof Long) {
                super.visitLdcInsn((Long)cst * -2);
                super.visitInsn(Opcodes.LADD);
            } else {
                super.visitLdcInsn(cst);
            }
        } else {
            super.visitLdcInsn(cst);
        }
    
    }
}

