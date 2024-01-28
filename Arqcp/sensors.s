.section .data

.section .text
	.global sens_temp
	.global sens_velc_vento
	.global sens_dir_vento
	.global sens_humd_atm
	.global sens_humd_solo
	.global sens_pluvio
	
	
sens_temp:
	movb %dil, %al
	cmpb $3, %sil
	jg greater1
	cmpb $-3, %sil
	jl less1
	addb %sil, %al
	jmp final1
greater1:
    addb $3, %al
    jmp final1
less1:
    addb $-3, %al
final1:
	cmpb $0, %al
	jge Pass
	movb %al, %r8b
	subb %al, %r8b
	subb %al, %r8b
	movb %r8b, %al
Pass:
	ret

sens_velc_vento:
	movb %dil, %al
	cmpb $20, %sil
	jg greater2
	cmpb $-20, %sil
	jl less2
	addb %sil, %al
    jmp final2
greater2:
    addb $20, %al
    jmp final2
less2:
    addb $-20, %al
    cmpb $0, %al
    jl menorzero
    jmp final2
menorzero:
    movb %al, %r8b
	subb %al, %r8b
	subb %al, %r8b
	movb %r8b, %al

final2:
	ret
		
		
sens_dir_vento:
	movw $360, %cx
	movw %di, %ax
	addw %si, %ax
	cwtd
	idivw %cx
	movw %dx, %ax
	cmpw $0, %ax
	jg End
	movw %ax, %r8w
	subw %ax, %r8w
	subw %ax, %r8w
	movw %r8w, %ax
End:
	ret
		
	
sens_humd_atm:
	movb $100, %cl
	movb %sil, %al
	imulb %dl
	idivb %cl
	cmpb $1, %dil
	jle Random
	addb %dil, %al
	jmp Continue
	Random:
	addb $3, %al
	Continue:
	cbtw
	idivb %cl
	movb %ah, %al
	cmpb $0, %al
	jge EndAtm
	movb %al, %r8b
	subb %al, %r8b
	subb %al, %r8b
	movb %r8b, %al
EndAtm:	
	cbtw
	ret
	
sens_humd_solo:
    movb $100, %cl
	movb %sil, %al
	imulb %dl
	idivb %cl
	cmpb $1, %dil
	jle Random2
	addb %dil, %al
	jmp Continue2
	Random2:
	addb $3, %al
	Continue2:
	cbtw
	idivb %cl
	movb %ah, %al
	cmpb $0, %al
	jge final4
	movb %al, %r8b
	subb %al, %r8b
	subb %al, %r8b
	movb %r8b, %al
final4:
	cbtw
    ret


sens_pluvio:
	movb %dl, %al
	movb $10, %r8b
	cbtw
	idiv %r8b
    movb %ah, %al
	subb %sil, %al
	addb %dil, %al
	cmpb $0, %al
	jge final5
	
toPositive:
	
	movb %al, %r8b
	subb %al, %r8b
	subb %al, %r8b
	movb %r8b, %al
	

	
final5:	
	movb $40, %r8b
	cbtw
	idiv %r8b
    movb %ah, %al
	ret
		
