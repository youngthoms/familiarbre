export interface TreeNode {
    id: string | number;
    pids?: Array<string | number>;
    mid?: string | number;
    fid?: string | number;
    name: string;
    img?: string;
}