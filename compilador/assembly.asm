PUSH 4
PUSH 4
PUSH 4
PUSH 4
PUSH 4
PUSH 8
PUSH 1
PUSH 24
PUSH 16
LOAD (4) 0[SB]
STORE (4) 0[SB]
LOADL 2
STORE (4) 12[SB]
LOADL 5
LOAD (4) 0[SB]
LOADL .2
STORE (8) 20[SB]
LOADL 3
LOADL 2
CALL gt
h:
JUMPIF(0) g
LOADL 2
STORE (4) 12[SB]
JUMP h
g:
LOADL 3.
LOADL .2
CALL gt
LOADL 1
LOADL 2
CALL lt
CALL and
JUMPIF (0) g
LOADL 3
LOADL 2
LOADL 1
CALL sub
LOADL 4
CALL mult
LOADA 53[SB]
CALL add
LOADI 4
LOADL 5
CALL mult
CALL add
STORE (4) 0[SB]
JUMP h
g:
LOAD (4) 4[SB]
STORE (4) 0[SB]
h:
LOADL 1
STORE (4) 0[SB]
HALT
