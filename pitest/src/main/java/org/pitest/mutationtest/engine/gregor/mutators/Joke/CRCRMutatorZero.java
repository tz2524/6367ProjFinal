package org.pitest.mutationtest.engine.gregor.mutators.Joke;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.pitest.mutationtest.engine.MutationIdentifier;
import org.pitest.mutationtest.engine.gregor.MethodInfo;
import org.pitest.mutationtest.engine.gregor.MethodMutatorFactory;
import org.pitest.mutationtest.engine.gregor.MutationContext;

public enum CRCRMutatorZero implements MethodMutatorFactory {

    CRCR_MUTATOR_ZERO;

    @Override
    public MethodVisitor create(final MutationContext context,
            final MethodInfo methodInfo, final MethodVisitor methodVisitor) {
        return new CRCRMutatorReplaceZeroMethodVisitor(this, context, methodVisitor);
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

class CRCRMutatorReplaceZeroMethodVisitor extends MethodVisitor {

    private final MethodMutatorFactory factory;
    private final MutationContext context;

    CRCRMutatorReplaceZeroMethodVisitor(final MethodMutatorFactory factory,
            final MutationContext context, final MethodVisitor delegateMethodVisitor) {
        super(Opcodes.ASM6, delegateMethodVisitor);
        this.factory = factory;
        this.context = context;
    }

    @Override
    public void visitLdcInsn(Object cst) {
        if (cst instanceof Number) {
            final MutationIdentifier muID = this.context.registerMutation(factory, "CRCR Mutator: Replaced value of constant with 0. " + cst.getClass().getTypeName());

            if (this.context.shouldMutate(muID)) {
                if (cst instanceof Integer) {
                    super.visitInsn(Opcodes.ICONST_0);
                } else if (cst instanceof Float) {
                    super.visitInsn(Opcodes.FCONST_0);
                } else if (cst instanceof Double) {
                    super.visitInsn(Opcodes.DCONST_0);
                } else if (cst instanceof Long) {
                    super.visitInsn(Opcodes.LCONST_0);
                } else {
                    super.visitLdcInsn(cst);
                }
            } else {
                super.visitLdcInsn(cst);
            }
        } else {
            super.visitLdcInsn(cst);
        }
    }

    @Override
    public void visitInsn(int opcode) {
        if ((opcode == Opcodes.ICONST_1) || (opcode == Opcodes.ICONST_2) || (opcode == Opcodes.ICONST_3) || (opcode == Opcodes.ICONST_4) || (opcode == Opcodes.ICONST_5) || (opcode == Opcodes.ICONST_M1) || (opcode == Opcodes.LCONST_1) || (opcode == Opcodes.FCONST_1) || (opcode == Opcodes.FCONST_2) || (opcode == Opcodes.DCONST_1)) {
            final MutationIdentifier muID = this.context.registerMutation(factory, "CRCR Mutator: Replaced  0");

            if (this.context.shouldMutate(muID)) {
                if ((opcode == Opcodes.ICONST_1) || (opcode == Opcodes.ICONST_2) || (opcode == Opcodes.ICONST_3) || (opcode == Opcodes.ICONST_4) || (opcode == Opcodes.ICONST_5) || (opcode == Opcodes.ICONST_M1)) {
                    super.visitInsn(Opcodes.ICONST_0);
                } else if ((opcode == Opcodes.LCONST_1)) {
                    super.visitInsn(Opcodes.LCONST_0);
                } else if ((opcode == Opcodes.FCONST_1) || (opcode == Opcodes.FCONST_2)) {
                    super.visitInsn(Opcodes.FCONST_0);
                } else if ((opcode == Opcodes.DCONST_1)) {
                    super.visitInsn(Opcodes.DCONST_0);
                }
            } else {
                super.visitInsn(opcode);
            }
        } else {
            super.visitInsn(opcode);
        }
    }

    @Override
    public void visitIntInsn(int opcode, int operand) {
        if ((opcode == Opcodes.BIPUSH) || (opcode == Opcodes.SIPUSH)) {
            final MutationIdentifier muID = this.context.registerMutation(factory, "CRCR Mutator: Replaced 0");

            if (this.context.shouldMutate(muID)) {

                super.visitInsn(Opcodes.ICONST_0);
            } else {
                super.visitIntInsn(opcode, operand);
            }
        } else {
            super.visitIntInsn(opcode, operand);
        }
    }

}