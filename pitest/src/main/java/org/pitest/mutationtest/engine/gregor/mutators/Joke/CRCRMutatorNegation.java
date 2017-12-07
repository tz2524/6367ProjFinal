package org.pitest.mutationtest.engine.gregor.mutators.Joke;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.pitest.mutationtest.engine.MutationIdentifier;
import org.pitest.mutationtest.engine.gregor.MethodInfo;
import org.pitest.mutationtest.engine.gregor.MethodMutatorFactory;
import org.pitest.mutationtest.engine.gregor.MutationContext;

public enum CRCRMutatorNegation implements MethodMutatorFactory {

    CRCR_MUTATOR_NEGATION;

    @Override
    public MethodVisitor create(final MutationContext context,
            final MethodInfo methodInfo, final MethodVisitor methodVisitor) {
        return new CRCRMutatorNegationMethodVisitor(this, context, methodVisitor);
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

class CRCRMutatorNegationMethodVisitor extends MethodVisitor {

    private final MethodMutatorFactory factory;
    private final MutationContext context;

    CRCRMutatorNegationMethodVisitor(final MethodMutatorFactory factory,
            final MutationContext context, final MethodVisitor delegateMethodVisitor) {
        super(Opcodes.ASM6, delegateMethodVisitor);
        this.factory = factory;
        this.context = context;
    }

    @Override
    public void visitLdcInsn(Object cst) {
        super.visitLdcInsn(cst);
        if (cst instanceof Number) {

            final MutationIdentifier muID = this.context.registerMutation(factory, "CRCR Mutator: Negated");

            if (this.context.shouldMutate(muID)) {
                if (cst instanceof Integer) {
                    super.visitInsn(Opcodes.INEG);
                } else if (cst instanceof Float) {
                    super.visitInsn(Opcodes.FNEG);
                } else if (cst instanceof Double) {
                    super.visitInsn(Opcodes.DNEG);
                } else if (cst instanceof Long) {
                    super.visitInsn(Opcodes.LNEG);
                }
            }
        }
    }

    @Override
    public void visitInsn(int opcode) {

        super.visitInsn(opcode);
        if ((opcode == Opcodes.ICONST_0) || (opcode == Opcodes.ICONST_1) || (opcode == Opcodes.ICONST_2) || (opcode == Opcodes.ICONST_3) || (opcode == Opcodes.ICONST_4) || (opcode == Opcodes.ICONST_5) || (opcode == Opcodes.ICONST_M1) || (opcode == Opcodes.LCONST_0) || (opcode == Opcodes.LCONST_1) || (opcode == Opcodes.FCONST_0) || (opcode == Opcodes.FCONST_1) || (opcode == Opcodes.FCONST_2) || (opcode == Opcodes.DCONST_0) || (opcode == Opcodes.DCONST_1)) {

            final MutationIdentifier muID = this.context.registerMutation(factory, "CRCR Mutator: Negated value of common constant (visitInsn)");

            if (this.context.shouldMutate(muID)) {
                if ((opcode == Opcodes.ICONST_0) || (opcode == Opcodes.ICONST_1) || (opcode == Opcodes.ICONST_2) || (opcode == Opcodes.ICONST_3) || (opcode == Opcodes.ICONST_4) || (opcode == Opcodes.ICONST_5) || (opcode == Opcodes.ICONST_M1)) {
                    super.visitInsn(Opcodes.INEG);
                } else if ((opcode == Opcodes.LCONST_0) || (opcode == Opcodes.LCONST_1)) {
                    super.visitInsn(Opcodes.LNEG);
                } else if ((opcode == Opcodes.FCONST_0) || (opcode == Opcodes.FCONST_1) || (opcode == Opcodes.FCONST_2)) {
                    super.visitInsn(Opcodes.FNEG);
                } else if ((opcode == Opcodes.DCONST_0) || (opcode == Opcodes.DCONST_1)) {
                    super.visitInsn(Opcodes.DNEG);
                }
            }
        }
    }

    @Override
    public void visitIntInsn(int opcode, int operand) {

        super.visitIntInsn(opcode, operand);

        if ((opcode == Opcodes.BIPUSH) || (opcode == Opcodes.SIPUSH)) {
            final MutationIdentifier muID = this.context.registerMutation(factory, "CRCR Mutator: Negated");

            if (this.context.shouldMutate(muID)) {
                super.visitInsn(Opcodes.INEG);
            }
        }
    }

}

