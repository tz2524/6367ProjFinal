package org.pitest.mutationtest.engine.gregor.mutators.Joke;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.pitest.mutationtest.engine.MutationIdentifier;
import org.pitest.mutationtest.engine.gregor.MethodInfo;
import org.pitest.mutationtest.engine.gregor.MethodMutatorFactory;
import org.pitest.mutationtest.engine.gregor.MutationContext;

public enum CRCRMutatorIncrement implements MethodMutatorFactory {

    CRCR_MUTATOR_INCREMENT;

    @Override
    public MethodVisitor create(final MutationContext context,
            final MethodInfo methodInfo, final MethodVisitor methodVisitor) {
        return new CRCRMutatorIncrementMethodVisitor(this, context, methodVisitor);
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

class CRCRMutatorIncrementMethodVisitor extends MethodVisitor {

    private final MethodMutatorFactory factory;
    private final MutationContext context;

    CRCRMutatorIncrementMethodVisitor(final MethodMutatorFactory factory,
            final MutationContext context, final MethodVisitor delegateMethodVisitor) {
        super(Opcodes.ASM6, delegateMethodVisitor);
        this.factory = factory;
        this.context = context;
    }

    @Override
    public void visitLdcInsn(Object cst) {
        final MutationIdentifier id = this.context.registerMutation(factory,"CRCR - Increment by 1");
        
        if (this.context.shouldMutate(id)) {
            if (cst instanceof Integer) {
                super.visitLdcInsn((Integer)cst + 1);
            } else if (cst instanceof Double) {
                super.visitLdcInsn((Double)cst + 1d);
            } else if (cst instanceof Float) {
                super.visitLdcInsn((Float)cst + 1f);
            } else if (cst instanceof Long) {
                super.visitLdcInsn((Long)cst + 1L);
            } else {
                super.visitLdcInsn(cst);
            }
        } else {
            super.visitLdcInsn(cst);
        }
    }

    @Override
    public void visitInsn(int opcode) {

        super.visitInsn(opcode);
        final MutationIdentifier id = this.context.registerMutation(factory,"CRCR - Replace with 1");
        
        if (this.context.shouldMutate(id)) {
            if (opcode <= Opcodes.ICONST_5 && opcode >= Opcodes.ICONST_M1) {
                super.visitInsn(Opcodes.ICONST_1);
                super.visitInsn(Opcodes.IADD);
            } else if (opcode == Opcodes.DCONST_1 || opcode == Opcodes.DCONST_1) {
                super.visitInsn(Opcodes.DCONST_1);
                super.visitInsn(Opcodes.DADD);
            } else if (opcode <= Opcodes.FCONST_2 && opcode >= Opcodes.FCONST_0) {
                super.visitInsn(Opcodes.FCONST_1);
                super.visitInsn(Opcodes.FADD);
            } else if (opcode <= Opcodes.LCONST_1 && opcode >= Opcodes.LCONST_0) {
                super.visitInsn(Opcodes.LCONST_1);
                super.visitInsn(Opcodes.LADD);
            } else {
                super.visitInsn(opcode);
            }
        } else {
            super.visitInsn(opcode);
        }
    }
}
