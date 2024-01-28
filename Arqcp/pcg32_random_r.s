.section .data
	
	state:
		.quad 0
	.global state
	
	inc:
		.quad 0
	.global inc
	
.section .text
.global pcg32_random_r
pcg32_random_r:

	pushq %rbp
	movq %rsp, %rbp
	subq $16, %rsp			#store space for local variables
	
	movq state(%rip), %rax
	movq %rax, -8(%rbp)
	movq $6364136223846793005ULL, %rcx
	movq -8(%rbp), %rax
	imulq %rcx				#oldState * big number
	movq $1, %rdx
	orq inc(%rip), %rdx		#inc | 1
	addq %rdx, %rax
	movq %rax, state(%rip)
	
	movq -8(%rbp), %rcx
	movq %rcx, %rdx
	shrq $18, %rdx
	xorq %rdx, %rcx			#oldState >> 18u ^ oldState
	shrq $27, %rcx
	movl %ecx, -12(%rbp)	#xorshifted
	
	movq -8(%rbp), %rax
	shrq $59, %rax
	movl %eax, -16(%rbp)	#rot
	
	movl -12(%rbp), %eax
	movl %eax, %edx
	movl -16(%rbp), %ecx
	sarl %cl, %edx			#fisrt part of the return
	movl $31, %r9d	
	movl %ecx, %edi
	sall %edi				#2 * rot
	subl %edi, %ecx			#-rot
	andl %ecx, %r9d			#-rot & 31
	sall %cl, %eax			#xorshifted << ((-rot) & 31)
	orl %edx, %eax			#xorshifted >> rot) | (xorshifted << ((-rot) & 31)
	
	movq %rbp, %rsp
	popq %rbp
	ret
	
	
	
	
	
	
	
	
	
	
	
